package ru.practicum.bank.transfer.clients.notifications;

import reactor.core.publisher.Mono;
import ru.practicum.bank.transfer.dto.TransferDto;

/**
 * Класс для запросов в микросервис Notifications.
 */
public interface NotificationsClient {

  /**
   * Метод для уведомления о переводе.
   * @param transferDto - объект с данными о переводе.
   */
  Mono<Void> requestTransferNotifications(TransferDto transferDto);
}