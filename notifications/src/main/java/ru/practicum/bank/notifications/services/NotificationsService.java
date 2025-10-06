package ru.practicum.bank.notifications.services;

import ru.practicum.bank.notifications.dto.CashDto;
import ru.practicum.bank.notifications.dto.TransferDto;
import ru.practicum.bank.notifications.dto.UserNotifyDto;

/**
 * Сервис для отправки уведомлений.
 */
public interface NotificationsService {

  /**
   * Уведомление о переводе средств на другой счет.
   * @param transferDto - данные по переводу средств.
   */
  void transferNotification(TransferDto transferDto);

  /**
   * Уведомление о действиях на счёте.
   * @param cashDto - данные по действиям на счёте.
   */
  void cashNotification(CashDto cashDto);

  /**
   * Уведомление о создании пользователя и счетов.
   * @param userNotifyDto - данные по новому пользователю и счетам.
   */
  void userNotification(UserNotifyDto userNotifyDto);
}
