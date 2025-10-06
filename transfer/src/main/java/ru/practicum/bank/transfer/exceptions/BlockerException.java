package ru.practicum.bank.transfer.exceptions;

/**
 * Исключение в случае некорректного ответа от сервиса блокировки.
 */
public class BlockerException extends RuntimeException {
  public BlockerException(String message) {
    super(message);
  }
}
