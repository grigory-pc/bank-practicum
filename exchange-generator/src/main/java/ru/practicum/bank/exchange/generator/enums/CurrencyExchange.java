package ru.practicum.bank.exchange.generator.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import ru.practicum.bank.exchange.generator.exceptions.CurrencyException;

/**
 * Поддерживаемые сервисом валюты.
 */
@RequiredArgsConstructor
public enum CurrencyExchange {
  RUB_RUB("RUB_RUB"),
  RUB_USD("RUB_USD"),
  RUB_CNY("RUB_CNY"),
  USD_CNY("USD_CNY");

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