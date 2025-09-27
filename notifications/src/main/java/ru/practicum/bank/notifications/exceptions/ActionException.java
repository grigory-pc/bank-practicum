package ru.practicum.bank.notifications.exceptions;

/**
 * Исключение в случае некорректного типа действий с денежными средствами.
 */
public class ActionException extends RuntimeException {
  public ActionException(String message) {
    super(message);
  }
}
