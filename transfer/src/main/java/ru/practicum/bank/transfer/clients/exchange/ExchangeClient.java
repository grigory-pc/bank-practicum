package ru.practicum.bank.transfer.clients.exchange;

import ru.practicum.bank.transfer.dto.Rate;
import ru.practicum.bank.transfer.enums.CurrencyExchange;

/**
 * Класс для запросов в микросервис ExchangeGenerator.
 */
public interface ExchangeClient {

  /**
   *  Запрос курсов валют в микросервис Exchange.
   *
   * @param currencyExchange - валютная пара.
   *
   * @return список курсов валют.
   */
  Rate getTransferRate(CurrencyExchange currencyExchange);
}
