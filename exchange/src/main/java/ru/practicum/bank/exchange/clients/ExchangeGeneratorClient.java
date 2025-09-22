package ru.practicum.bank.exchange.clients;

import java.util.List;
import ru.practicum.bank.exchange.dto.Rate;

/**
 * Класс для запросов в микросервис ExchangeGenerator.
 */
public interface ExchangeGeneratorClient {

  /**
   * Выполнение запросов в сервис ExchangeGenerator.
   * @return курсы валют.
   */
  List<Rate> getRates();
}
