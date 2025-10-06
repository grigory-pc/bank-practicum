package ru.practicum.bank.accounts.exceptions;

/**
 * Исключение в случае если возраст пользователя при регистрации меньше 18 лет.
 */
public class AgeException extends RuntimeException {
  public AgeException(String message) {
    super(message);
  }
}
