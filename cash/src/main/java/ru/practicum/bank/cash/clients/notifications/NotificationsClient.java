package ru.practicum.bank.cash.clients.notifications;

import reactor.core.publisher.Mono;
import ru.practicum.bank.cash.dto.CashDto;

/**
 * Класс для запросов в микросервис Notifications.
 */
public interface NotificationsClient {

  /**
   * Метод для уведомления об операциях на счете.
   * @param cashDto - объект с данными по операции на счете.
   */
  Mono<Void> requestCashNotifications(CashDto cashDto);
}