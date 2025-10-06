package ru.practicum.bank.front.ui.clients.cash;

import reactor.core.publisher.Mono;
import ru.practicum.bank.front.ui.dto.CashDto;

/**
 * Класс для запросов в микросервис Cash.
 */
public interface CashClient {

  /**
   *
   * @param cashDto - объект с данными для перевода.
   */
  Mono<Void> requestCashOperation(CashDto cashDto);
}
