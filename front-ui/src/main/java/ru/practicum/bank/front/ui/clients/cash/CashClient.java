package ru.practicum.bank.front.ui.clients.cash;

import reactor.core.publisher.Mono;
import ru.practicum.bank.front.ui.dto.CashDto;

/**
 * Класс для запросов в микросервис Cash.
 */
public interface CashClient {

  /**
   * Запрос на изменение баланса на счете в микросервис Cash.
   */
  Mono<Void> requestCashOperation(CashDto cashDto);
}
