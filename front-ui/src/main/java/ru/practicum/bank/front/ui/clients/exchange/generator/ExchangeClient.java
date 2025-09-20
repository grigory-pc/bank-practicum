package ru.practicum.bank.front.ui.clients.exchange.generator;

import java.util.List;
import ru.practicum.bank.front.ui.dto.Rate;

/**
 * Класс для запросов в микросервис ExchangeGenerator.
 */
public interface ExchangeClient {

  /**
   * Выполнение запросов в сервис ExchangeGenerator.
   * @return курсы валют.
   */
  List<Rate> getRates();
}
