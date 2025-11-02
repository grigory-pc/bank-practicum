package ru.practicum.bank.cash.services;

import ru.practicum.bank.cash.dto.CashDto;

/**
 * Сервис для работы с kafka.
 */
public interface KafkaService {

  /**
   * Сборка и отправка сообщения в kafka.
   *
   * @param cashDto - данные для операций на счёте.
   */
  void sendMessage(CashDto cashDto);
}