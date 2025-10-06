package ru.practicum.bank.cash.exceptions;

/**
 * Исключение в случае некорректного типа действий с денежными средствами.
 */
public class ActionException extends RuntimeException {
  public ActionException(String message) {
    super(message);
  }
}
