package ru.practicum.bank.front.ui.clients.exchange;

import java.util.List;
import reactor.core.publisher.Mono;
import ru.practicum.bank.front.ui.dto.Rate;

/**
 * Класс для запросов в микросервис Exchange.
 */
public interface ExchangeClient {

  /**
   * Запрос списка курсов валют в микросервис Exchange.
   * @return список курсов валют.
   */
  Mono<List<Rate>> getRates();
}
