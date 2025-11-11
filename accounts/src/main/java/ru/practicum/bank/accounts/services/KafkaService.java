package ru.practicum.bank.accounts.services;

import ru.practicum.bank.accounts.dto.UserNotifyDto;

/**
 * Сервис для работы с kafka.
 */
public interface KafkaService {

  /**
   * Сборка и отправка сообщения в kafka.
   *
   * @param userNotifyDto - объект с данными по созданному пользователю и аккаунтам.
   */
  void sendMessage(UserNotifyDto userNotifyDto);
}