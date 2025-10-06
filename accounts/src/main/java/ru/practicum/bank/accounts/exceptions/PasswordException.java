package ru.practicum.bank.accounts.exceptions;

/**
 * Исключение в случае несовпадения паролей.
 */
public class PasswordException extends RuntimeException {
  public PasswordException(String message) {
    super(message);
  }
}
