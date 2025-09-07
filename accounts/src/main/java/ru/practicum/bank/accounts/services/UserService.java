package ru.practicum.bank.accounts.services;

import ru.practicum.bank.accounts.dto.UserDto;

/**
 * Сервис для обработки запросов на создание и модификацию пользователя.
 */
public interface UserService {

  /**
   * Метод для создания нового пользователя.
   * @param userDto - объект пользователя.
   */
  void addUser(UserDto userDto);

  /**
   * Метод для обновления пароля пользователя.
   * @param userDto - объект пользователя.
   */
  void updateUserPassword(UserDto userDto);

  /**
   * Метод для обновления данных пользователя.
   * @param userDto - объект пользователя.
   */
  void updateUserData(UserDto userDto);
}
