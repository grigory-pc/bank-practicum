package ru.practicum.bank.transfer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import ru.practicum.bank.transfer.exceptions.CurrencyException;

/**
 * Поддерживаемые сервисом валюты.
 */
@RequiredArgsConstructor
public enum Currency {
  RUB_USD("RUB"),
  RUB_CNY("CNY"),
  USD_CNY("USD");

  @JsonValue
  public final String value;

  @JsonCreator
  public static Currency getValueOf(String action) throws CurrencyException {
    for (Currency type : Currency.values()) {
      if (type.value.equalsIgnoreCase(action)) {
        return type;
      }
    }
    throw new CurrencyException("Некорректный тип действия с балансом");
  }
}