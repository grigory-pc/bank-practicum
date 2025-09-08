package ru.practicum.bank.accounts.exceptions;

/**
 * Исключение в случае некорректного типа действий с денежными средствами.
 */
public class PasswordException extends RuntimeException {
  public PasswordException(String message) {
    super(message);
  }
}
