package ru.practicum.bank.exchange.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import ru.practicum.bank.exchange.exceptions.CurrencyException;

/**
 * Поддерживаемые сервисом валюты.
 */
@RequiredArgsConstructor
public enum CurrencyExchange {
  RUB_USD("RUB_USD"),
  USD_RUB("USD_RUB"),
  RUB_CNY("RUB_CNY"),
  CNY_RUB("CNY_RUB"),
  USD_CNY("USD_CNY"),
  CNY_USD("CNY_USD");

  @JsonValue
  public final String value;

  @JsonCreator
  public static CurrencyExchange getValueOf(String action) throws CurrencyException {
    for (CurrencyExchange type : CurrencyExchange.values()) {
      if (type.value.equalsIgnoreCase(action)) {
        return type;
      }
    }
    throw new CurrencyException("Некорректный тип действия с балансом");
  }
}