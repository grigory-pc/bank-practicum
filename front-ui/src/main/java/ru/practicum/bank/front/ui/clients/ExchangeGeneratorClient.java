package ru.practicum.bank.front.ui.clients;

import java.util.List;
import ru.practicum.bank.front.ui.dto.Rate;

/**
 * Класс для запросов в ExchangeGenerator.
 */
public interface ExchangeGeneratorClient {

  /**
   * Выполнение запросов в сервис ExchangeGenerator.
   * @return курсы валют.
   */
  List<Rate> getRates();
}
