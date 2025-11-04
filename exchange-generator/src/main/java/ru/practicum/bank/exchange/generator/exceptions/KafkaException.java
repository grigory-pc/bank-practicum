package ru.practicum.bank.exchange.generator.exceptions;

/**
 * Ошибка при работе с kafka.
 */
public class KafkaException extends RuntimeException {

  public KafkaException(String message) {
    super(message);
  }
}
