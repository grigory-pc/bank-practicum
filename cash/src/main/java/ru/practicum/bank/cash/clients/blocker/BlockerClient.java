package ru.practicum.bank.cash.clients.blocker;

import reactor.core.publisher.Mono;

/**
 * Класс для запросов в микросервис Blocker.
 */
public interface BlockerClient {

  /**
   * Метод для запроса блокировки.
   *
   * @return возвращает результата блокировки (true или false).
   */
  Mono<Boolean> requestBlockOperation();
}
