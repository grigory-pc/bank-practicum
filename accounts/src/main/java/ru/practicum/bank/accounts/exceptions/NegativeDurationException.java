package ru.practicum.bank.accounts.exceptions;

public class NegativeDurationException extends Exception {
  public NegativeDurationException(String message) {
    super(message);
  }
}
