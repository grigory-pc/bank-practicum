package ru.practicum.bank.front.ui.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExchangeGeneratorHttpException extends RuntimeException {
  public ExchangeGeneratorHttpException(String message) {
    super(message);
  }
}