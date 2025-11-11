package ru.practicum.bank.exchange.generator.service;

import java.util.List;
import ru.practicum.bank.exchange.generator.dto.RateDto;

/**
 * Сервис для работы с kafka.
 */
public interface KafkaService {

  /**
   * Сборка и отправка сообщения в kafka.
   *
   * @param rates - список всех курсов валют.
   */
  void sendMessage(List<RateDto> rates);
}