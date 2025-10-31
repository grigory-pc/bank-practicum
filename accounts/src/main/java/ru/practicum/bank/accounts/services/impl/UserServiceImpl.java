package ru.practicum.bank.accounts.services.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.accounts.clients.notifications.NotificationsClient;
import ru.practicum.bank.accounts.dto.UserAuthDto;
import ru.practicum.bank.accounts.dto.UserDto;
import ru.practicum.bank.accounts.dto.UserFullDto;
import ru.practicum.bank.accounts.dto.UserNotifyDto;
import ru.practicum.bank.accounts.entity.Account;
import ru.practicum.bank.accounts.entity.User;
import ru.practicum.bank.accounts.exceptions.AgeException;
import ru.practicum.bank.accounts.exceptions.PasswordException;
import ru.practicum.bank.accounts.mappers.AccountMapper;
import ru.practicum.bank.accounts.mappers.CurrencyMapper;
import ru.practicum.bank.accounts.mappers.UserMapper;
import ru.practicum.bank.accounts.repository.AccountRepository;
import ru.practicum.bank.accounts.repository.CurrencyRepository;
import ru.practicum.bank.accounts.repository.UserRepository;
import ru.practicum.bank.accounts.services.CheckService;
import ru.practicum.bank.accounts.services.KafkaService;
import ru.practicum.bank.accounts.services.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  public static final String PASS_PREFIX = "{noop}";
  public static final double NEW_ACCOUNT_VALUE = 0.00;
  public static final boolean NEW_ACCOUNT_EXISTS = false;
  private final CheckService checkService;
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;
  private final CurrencyRepository currencyRepository;
  private final CurrencyMapper currencyMapper;
  private final NotificationsClient notificationsClient;
  private final KafkaService kafkaService;

  @Override
  public void addUser(UserDto userDto) throws PasswordException {
    if (userRepository.findByLogin(userDto.login()) == null) {
      if (Boolean.FALSE.equals(
          checkService.checkPassword(userDto.password(), userDto.confirm_password()))) {
        throw new PasswordException("Не совпадают пароли");
      }
      if (Boolean.FALSE.equals(checkService.checkBirthdate(userDto.birthdate()))) {
        throw new AgeException("Возраст меньше 18 лет");
      }
      log.info("Отправлен запрос в БД на добавление пользователя {}", userDto.login());

      var savedUser = userRepository.save(userMapper.toUser(userDto));

      var newAccounts = createNewUserAccounts(savedUser);
      accountRepository.saveAll(newAccounts);

      sendNotifications(savedUser, newAccounts);
    }
  }

  @Override
  public void updateUserPassword(UserDto userDto) throws PasswordException {
    if (Boolean.TRUE.equals(
        checkService.checkPassword(userDto.password(), userDto.confirm_password()))) {
      var user = userRepository.findByLogin(userDto.login());
      user.setPassword(userDto.password());

      log.info("Отправлен запрос в БД на обновление пароля пользователя {}", user.getLogin());

      userRepository.save(user);
    } else {
      throw new PasswordException("Не совпадают пароли");
    }
  }

  @Override
  public void updateUserData(UserDto userDto) {
    var user = userRepository.findByLogin(userDto.login());

    if (userDto.name() != null) {
      user.setName(userDto.name());
    }
    if (userDto.birthdate() != null && Boolean.TRUE.equals(
        checkService.checkBirthdate(userDto.birthdate()))) {

      user.setBirthdate(userDto.birthdate());
    }

    log.info("Отправлен запрос в БД на обновление данных пользователя {}", user.getLogin());

    userRepository.save(user);
  }

  @Override
  public UserFullDto getUserFullByLogin(String login) {
    var user = userRepository.findByLogin(login);

    var userFullDto = userMapper.toFullDto(user);
    var currencyDtos = currencyMapper.toDto(currencyRepository.findAll());
    var userShortDtos = userMapper.toShortDto(userRepository.findAll());
    var updatedUserShortDtos = userShortDtos.stream()
                                            .filter(userDto -> !userDto.login().equals(login))
                                            .toList();
    var accountsDto = accountMapper.toDto(accountRepository.findAllByUserId(user.getId()));

    userFullDto.setCurrency(currencyDtos);
    userFullDto.setUsers(updatedUserShortDtos);
    userFullDto.setPasswordErrors(new ArrayList<>());
    userFullDto.setUserAccountsErrors(new ArrayList<>());
    userFullDto.setCashErrors(new ArrayList<>());
    userFullDto.setTransferErrors(new ArrayList<>());
    userFullDto.setTransferOtherErrors(new ArrayList<>());
    userFullDto.setAccounts(accountsDto);

    return userFullDto;
  }

  @Override
  public UserAuthDto getUserByLogin(String login) {
    var user = userRepository.findByLogin(login);
    user.setPassword(String.format("%s%s", PASS_PREFIX, user.getPassword()));

    return userMapper.toAuthDto(user);
  }

  private List<Account> createNewUserAccounts(User user) {
    var allCurrencies = currencyRepository.findAll();

    return allCurrencies.stream()
                        .map(currency -> Account.builder()
                                                .value(NEW_ACCOUNT_VALUE)
                                                .isExists(NEW_ACCOUNT_EXISTS)
                                                .user(user)
                                                .currency(currency)
                                                .build())
                        .toList();

  }

  private void sendNotifications(User savedUser, List<Account> newAccounts) {
    var userNotify = UserNotifyDto.builder()
                                  .login(savedUser.getLogin())
                                  .name(savedUser.getName())
                                  .accounts(accountMapper.toDto(newAccounts))
                                  .build();

    kafkaService.sendMessage(userNotify);

    notificationsClient.requestAccountNotifications(userNotify).block();
  }
}