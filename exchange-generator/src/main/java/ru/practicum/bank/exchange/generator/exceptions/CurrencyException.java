package ru.practicum.bank.exchange.generator.exceptions;

/**
 * Исключение в случае некорректного типа валюты.
 */
public class CurrencyException extends RuntimeException {
  public CurrencyException(String message) {
    super(message);
  }
}