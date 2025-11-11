package ru.practicum.bank.transfer.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.practicum.bank.transfer.configs.KafkaConfig;
import ru.practicum.bank.transfer.dto.TransferDto;
import ru.practicum.bank.transfer.exceptions.KafkaException;
import ru.practicum.bank.transfer.services.KafkaService;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final String MESSAGE_NOT_DELIVERED_FORMAT
      = "Сообщение не доставлено в топик %s: {%s}";
  private static final String TRANSFER_NOTIFICATION = "transfer_notification_";
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final KafkaConfig kafkaConfig;

  @Override
  public void sendMessage(TransferDto transferDto) {
    for (var topic : kafkaConfig.out()) {
      try {
        var keyId = UUID.randomUUID().toString();
        var message = MAPPER.writeValueAsString(transferDto);
        kafkaTemplate.send(
                         new ProducerRecord<>(topic, String.format("%s%s", TRANSFER_NOTIFICATION, keyId),
                                              message))
                     .join();

        log.info("В кафка отправлено сообщение с keyId = {}", keyId);
      } catch (Exception e) {
        throw new KafkaException(MESSAGE_NOT_DELIVERED_FORMAT.formatted(topic, e.getMessage()));
      }
    }
  }
}
