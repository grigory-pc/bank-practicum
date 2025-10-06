package ru.practicum.bank.transfer.exceptions;

public class NegativeDurationException extends Exception {
  public NegativeDurationException(String message) {
    super(message);
  }
}
