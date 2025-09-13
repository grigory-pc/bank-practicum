package ru.practicum.bank.front.ui.clients.accounts;

import reactor.core.publisher.Mono;
import ru.practicum.bank.front.ui.dto.UserDto;
import ru.practicum.bank.front.ui.dto.UserFullDto;
import ru.practicum.bank.front.ui.dto.UserAuthDto;

/**
 * Класс для запросов в микросервис Accounts.
 */
public interface AccountsClient {
  /**
   * Запрос на создание пользователя в микросервис Accounts.
   *
   * @param user - объект пользователя.
   * @return объект mono.
   */
  Mono<Void> requestCreateUser(UserDto user);

  /**
   * Запрос в микросервис Accounts для изменения пароля или данных пользователя.
   *
   * @param path - путь запроса.
   * @param user - объект пользователя.
   * @return объект mono.
   */
  Mono<Void> requestEditUser(String path, UserDto user);

  /**
   * Запрос в микросервис Accounts для получения полной информации о пользователе.
   *
   * @param login - логин пользователя.
   * @return объект mono UserFullDto.
   */
  Mono<UserFullDto> requestGetUser(String login);

  /**
   * Запрос в микросервис Accounts для получения информации о пользователе для авторизации.
   *
   * @param login - логин пользователя.
   * @return объект mono UserLoginAuth.
   */
  Mono<UserAuthDto> requestGetAuthUser(String login);
}
