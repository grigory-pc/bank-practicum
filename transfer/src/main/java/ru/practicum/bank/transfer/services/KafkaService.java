package ru.practicum.bank.transfer.services;

import ru.practicum.bank.transfer.dto.TransferDto;

/**
 * Сервис для работы с kafka.
 */
public interface KafkaService {

  /**
   * Сборка и отправка сообщения в kafka.
   *
   * @param transferDto - объект с данными для перевода.
   */
  void sendMessage(TransferDto transferDto);
}