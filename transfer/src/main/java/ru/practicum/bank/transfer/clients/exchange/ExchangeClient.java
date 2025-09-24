package ru.practicum.bank.transfer.clients.exchange;

import java.util.List;
import ru.practicum.bank.transfer.dto.Rate;

/**
 * Класс для запросов в микросервис ExchangeGenerator.
 */
public interface ExchangeClient {

  /**
   *  Запрос курсов валют в микросервис Exchange.
   *
   * @param fromCurrency - валюта, с которой будет перевод.
   * @param toCurrency - валюта, на которую будет перевод.
   *
   * @return список курсов валют.
   */
  List<Rate> getTransferRates(String fromCurrency, String toCurrency);
}
