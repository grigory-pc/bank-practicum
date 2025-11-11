package ru.practicum.bank.exchange.services;

import java.util.List;
import ru.practicum.bank.exchange.dto.RateDto;

/**
 * Сервис для работы с курсами валют.
 */
public interface RateService {

  /**
   * Метод для сохранения курса валют.
   *
   * @param rates - данные курсов валюты.
   */
  void updateRates(List<RateDto> rates);

  /**
   * Метод для получения курса валюты.
   *
   * @param currencyExchange - валютная пара.
   * @return курс валюты.
   */
  RateDto getCurrencyRate(String currencyExchange);

  /**
   * Метод для получения списка всех курсов валют.
   *
   * @return список курсов валют.
   */
  List<RateDto> getRates();
}
