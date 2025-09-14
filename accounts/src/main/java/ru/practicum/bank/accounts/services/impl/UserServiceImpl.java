package ru.practicum.bank.accounts.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.accounts.dto.UserAuthDto;
import ru.practicum.bank.accounts.dto.UserDto;
import ru.practicum.bank.accounts.dto.UserFullDto;
import ru.practicum.bank.accounts.exceptions.AgeException;
import ru.practicum.bank.accounts.exceptions.PasswordException;
import ru.practicum.bank.accounts.mappers.UserMapper;
import ru.practicum.bank.accounts.repository.UserRepository;
import ru.practicum.bank.accounts.services.CheckService;
import ru.practicum.bank.accounts.services.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final CheckService checkService;
  private final UserRepository userRepository;
  private final UserMapper userMapper;

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
    return null;
  }

  @Override
  public UserAuthDto getUserByLogin(String login) {
    return userMapper.toAuthDto(userRepository.findByLogin(login));
  }
}