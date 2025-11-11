package ru.practicum.bank.cash.exceptions;

/**
 * Ошибка при работе с kafka.
 */
public class KafkaException extends RuntimeException {

  public KafkaException(String message) {
    super(message);
  }
}
