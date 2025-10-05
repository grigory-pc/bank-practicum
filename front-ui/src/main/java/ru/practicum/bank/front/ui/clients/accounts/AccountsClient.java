package ru.practicum.bank.front.ui.clients.accounts;

import ru.practicum.bank.front.ui.dto.UserAuthDto;
import ru.practicum.bank.front.ui.dto.UserDto;
import ru.practicum.bank.front.ui.dto.UserFullDto;

/**
 * Класс для запросов в микросервис Accounts.
 */
public interface AccountsClient {
  /**
   * Запрос на создание пользователя в микросервис Accounts.
   *
   * @param user - объект пользователя.
   */
  void requestCreateUser(UserDto user);

  /**
   * Запрос в микросервис Accounts для изменения пароля или данных пользователя.
   *
   * @param path - путь запроса.
   * @param user - объект пользователя.
   */
  void requestEditUser(String path, UserDto user);

  /**
   * Запрос в микросервис Accounts для получения полной информации о пользователе.
   *
   * @param login - логин пользователя.
   * @return объект UserFullDto.
   */
  UserFullDto requestGetUser(String login);

  /**
   * Запрос в микросервис Accounts для получения информации о пользователе для авторизации.
   *
   * @param login - логин пользователя.
   * @return объект UserLoginAuth.
   */
  UserAuthDto requestGetAuthUser(String login);
}
