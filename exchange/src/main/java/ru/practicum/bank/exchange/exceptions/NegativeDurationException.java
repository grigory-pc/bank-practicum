package ru.practicum.bank.exchange.exceptions;

public class NegativeDurationException extends Exception {
  public NegativeDurationException(String message) {
    super(message);
  }
}
