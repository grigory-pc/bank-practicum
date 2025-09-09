package ru.practicum.bank.exchange.generator.enums;

import ru.practicum.bank.exchange.generator.exceptions.CurrencyException;

/**
 * Поддерживаемые валюты сервисом.
 */
public enum Currency {
  RUB_USD,
  RUB_CNY,
  USD_CNY;

  public static Currency getValueOf(String currency) throws CurrencyException {
    for (Currency type : Currency.values()) {
      if (type.name().equalsIgnoreCase(currency)) {
        return type;
      }
    }
    throw new CurrencyException("Некорректный тип действия с балансом");
  }
}