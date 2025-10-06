package ru.practicum.bank.accounts.services;

import ru.practicum.bank.accounts.dto.UserAuthDto;
import ru.practicum.bank.accounts.dto.UserDto;
import ru.practicum.bank.accounts.dto.UserFullDto;
import ru.practicum.bank.accounts.exceptions.PasswordException;

/**
 * Сервис для обработки запросов на создание и модификацию пользователя.
 */
public interface UserService {

  /**
   * Метод для создания нового пользователя.
   *
   * @param userDto - объект пользователя.
   * @throws PasswordException - исключение в случае, если пароль не совпадает с повторным вводом.
   */
  void addUser(UserDto userDto) throws PasswordException;

  /**
   * Метод для обновления пароля пользователя.
   *
   * @param userDto - объект пользователя.
   * @throws PasswordException - исключение в случае, если пароль не совпадает с повторным вводом.
   */
  void updateUserPassword(UserDto userDto) throws PasswordException;

  /**
   * Метод для обновления данных пользователя.
   *
   * @param userDto - объект пользователя.
   */
  void updateUserData(UserDto userDto);

  /**
   * Метод для получения полной информации о пользователе.
   *
   * @param login - логин пользователя.
   */
  UserFullDto getUserFullByLogin(String login);

  /**
   * Метод для получения информации о пользователе.
   *
   * @param login - логин пользователя.
   */
  UserAuthDto getUserByLogin(String login);
}
