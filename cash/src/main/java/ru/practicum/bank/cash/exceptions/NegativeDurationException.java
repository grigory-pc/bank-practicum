package ru.practicum.bank.cash.exceptions;

public class NegativeDurationException extends Exception {
  public NegativeDurationException(String message) {
    super(message);
  }
}
