package ru.practicum.bank.accounts.services.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.accounts.dto.CurrencyDto;
import ru.practicum.bank.accounts.dto.UserAuthDto;
import ru.practicum.bank.accounts.dto.UserDto;
import ru.practicum.bank.accounts.dto.UserFullDto;
import ru.practicum.bank.accounts.dto.UserShortDto;
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
import ru.practicum.bank.accounts.services.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  public static final String PASS_PREFIX = "{noop}";
  private final CheckService checkService;
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;
  private final CurrencyRepository currencyRepository;
  private final CurrencyMapper currencyMapper;

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

      userRepository.save(userMapper.toUser(userDto));
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
    User user = userRepository.findByLogin(login);

    UserFullDto userFullDto = userMapper.toFullDto(user);

//    List<AccountsDto> accountsDtos = accountMapper.toDto(accountRepository.findAllByUser(user));
    List<CurrencyDto> currencyDtos = currencyMapper.toDto(currencyRepository.findAll());
    List<UserShortDto> userShortDtos = userMapper.toShortDto(userRepository.findAll());

//    userFullDto.setAccounts(accountsDtos);
    userFullDto.setCurrency(currencyDtos);
    userFullDto.setUsers(userShortDtos);
    userFullDto.setPasswordErrors(new ArrayList<>());
    userFullDto.setUserAccountsErrors(new ArrayList<>());
    userFullDto.setCashErrors(new ArrayList<>());
    userFullDto.setTransferErrors(new ArrayList<>());
    userFullDto.setTransferOtherErrors(new ArrayList<>());

    return userFullDto;
  }

  @Override
  public UserAuthDto getUserByLogin(String login) {
    User user = userRepository.findByLogin(login);
    user.setPassword(String.format("%s%s", PASS_PREFIX, user.getPassword()));

    return userMapper.toAuthDto(user);
  }
}