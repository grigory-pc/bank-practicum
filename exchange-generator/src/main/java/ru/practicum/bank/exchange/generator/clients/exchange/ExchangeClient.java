package ru.practicum.bank.exchange.generator.clients.exchange;

import java.util.List;
import reactor.core.publisher.Flux;
import ru.practicum.bank.exchange.generator.dto.RateDto;

/**
 * Класс для отправки курсов валют в микросервис Exchange.
 */
public interface ExchangeClient {

  /**
   * Отправка курса валюты в микросервис Exchange.
   *
   * @param rateDtos - валютная пара.
   */
  Flux<Void> postRates(List<RateDto> rateDtos);
}
