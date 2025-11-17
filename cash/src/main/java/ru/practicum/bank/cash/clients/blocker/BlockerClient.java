package ru.practicum.bank.cash.clients.blocker;

import reactor.core.publisher.Mono;

/**
 * Класс для запросов в микросервис Blocker.
 */
public interface BlockerClient {

  /**
   * Метод для запроса блокировки.
   * @param login - логин пользователя, для которого заблокирована операция.
   *
   * @return возвращает результата блокировки (true или false).
   */
  Mono<Boolean> requestBlockOperation(String login);
}
