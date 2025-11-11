package ru.practicum.bank.transfer.exceptions;

/**
 * Ошибка при работе с kafka.
 */
public class KafkaException extends RuntimeException {

  public KafkaException(String message) {
    super(message);
  }
}
