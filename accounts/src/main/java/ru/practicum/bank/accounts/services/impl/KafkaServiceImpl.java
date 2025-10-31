package ru.practicum.bank.accounts.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.practicum.bank.accounts.configs.KafkaConfig;
import ru.practicum.bank.accounts.dto.UserNotifyDto;
import ru.practicum.bank.accounts.exceptions.KafkaException;
import ru.practicum.bank.accounts.services.KafkaService;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {
  private static final String MESSAGE_NOT_DELIVERED_FORMAT
      = "Сообщение не доставлено в топик %s: {%s}";
  private final KafkaTemplate<String, UserNotifyDto> kafkaTemplate;
  private final KafkaConfig kafkaConfig;

  @Override
  public void sendMessage(UserNotifyDto userNotifyDto) {
    for (var topic : kafkaConfig.out()) {
      try {
        kafkaTemplate.send(new ProducerRecord<>(topic, userNotifyDto)).join();
      } catch (Exception e) {
        throw new KafkaException(MESSAGE_NOT_DELIVERED_FORMAT.formatted(topic, e.getMessage()));
      }
    }
  }
}
