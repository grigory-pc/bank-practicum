package ru.practicum.bank.exchange.clients;

import java.util.List;
import reactor.core.publisher.Mono;
import ru.practicum.bank.exchange.dto.Rate;

/**
 * Класс для запросов в микросервис ExchangeGenerator.
 */
public interface ExchangeGeneratorClient {

  /**
   * Получение списка курсов валют.
   * @return курсы валют.
   */
  List<Rate> getRates();

  /**
   * Получение курса валюты.
   *
   * @param currencyExchange - курс валюты.
   * @return курс валюты.
   */
  Mono<Rate> getCurrencyRate(String currencyExchange);
}
