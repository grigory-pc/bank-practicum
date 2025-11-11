package ru.practicum.bank.notifications.enums;

import lombok.RequiredArgsConstructor;

/**
 * Возможные виды уведомлений.
 */
@RequiredArgsConstructor
public enum NotificationType {
  USER("USER"),
  CASH("CASH"),
  TRANSFER("TRANSFER"),
  UNKNOWN("UNKNOWN");

  public final String value;
}