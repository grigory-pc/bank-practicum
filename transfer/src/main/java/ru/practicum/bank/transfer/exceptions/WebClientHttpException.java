package ru.practicum.bank.transfer.exceptions;

import lombok.NoArgsConstructor;

/**
 * Исключение при возникновении проблем во время обращения к внешнему микросервису.
 */
@NoArgsConstructor
public class WebClientHttpException extends RuntimeException {
  public WebClientHttpException(String message) {
    super(message);
  }
}