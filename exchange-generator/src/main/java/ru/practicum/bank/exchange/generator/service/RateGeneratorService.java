package ru.practicum.bank.exchange.generator.service;

import java.util.List;
import ru.practicum.bank.exchange.generator.dto.Rate;

/**
 * Сервис для получения курсов валют.
 */
public interface RateGeneratorService {
  /**
   * Метод для получения всех курсов валют
   */
  List<Rate> getAllCurrency();
}
