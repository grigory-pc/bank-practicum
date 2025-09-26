package ru.practicum.bank.transfer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import ru.practicum.bank.transfer.exceptions.CurrencyException;

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
  public static CurrencyExchange getValueOf(String currencyExchange) throws CurrencyException {
    for (CurrencyExchange currency : CurrencyExchange.values()) {
      if (currency.value.equalsIgnoreCase(currencyExchange)) {
        return currency;
      }
    }
    throw new CurrencyException("Некорректный тип действия с балансом");
  }
}