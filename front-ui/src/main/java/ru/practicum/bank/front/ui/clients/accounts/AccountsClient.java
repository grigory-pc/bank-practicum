package ru.practicum.bank.front.ui.clients.accounts;

import reactor.core.publisher.Mono;
import ru.practicum.bank.front.ui.dto.UserDto;

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
}
