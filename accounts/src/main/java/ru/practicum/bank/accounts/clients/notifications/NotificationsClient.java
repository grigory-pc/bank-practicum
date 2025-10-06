package ru.practicum.bank.accounts.clients.notifications;

import reactor.core.publisher.Mono;
import ru.practicum.bank.accounts.dto.UserNotifyDto;

/**
 * Класс для запросов в микросервис Notifications.
 */
public interface NotificationsClient {

  /**
   * Метод для уведомления о создании аккаунта.
   * @param userNotifyDto - объект с данными по созданному пользователю и аккаунтам.
   */
  Mono<Void> requestAccountNotifications(UserNotifyDto userNotifyDto);
}