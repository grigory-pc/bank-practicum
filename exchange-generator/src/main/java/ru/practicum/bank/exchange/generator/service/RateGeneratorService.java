package ru.practicum.bank.exchange.generator.service;

import java.util.List;
import ru.practicum.bank.exchange.generator.dto.RateDto;

/**
 * Сервис для получения курсов валют.
 */
public interface RateGeneratorService {

  /**
   * Метод для получения списка всех курсов валют
   */
  List<RateDto> getAllCurrency();
}