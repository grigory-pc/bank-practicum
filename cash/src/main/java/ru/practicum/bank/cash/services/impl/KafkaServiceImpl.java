package ru.practicum.bank.cash.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.practicum.bank.cash.configs.KafkaConfig;
import ru.practicum.bank.cash.dto.CashDto;
import ru.practicum.bank.cash.exceptions.KafkaException;
import ru.practicum.bank.cash.services.KafkaService;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final String MESSAGE_NOT_DELIVERED_FORMAT
      = "Сообщение не доставлено в топик %s: {%s}";
  private static final String CASH_NOTIFICATION = "cash_notification_";
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final KafkaConfig kafkaConfig;
  private final MeterRegistry meterRegistry;

  @Override
  public void sendMessage(CashDto cashDto) {
    for (var topic : kafkaConfig.out()) {
      try {
        var keyId = UUID.randomUUID().toString();
        var message = MAPPER.writeValueAsString(cashDto);
        kafkaTemplate.send(
                         new ProducerRecord<>(topic, String.format("%s%s", CASH_NOTIFICATION, keyId), message))
                     .join();

        log.info("В кафка отправлено сообщение с keyId = {}", keyId);
      } catch (Exception e) {
        meterRegistry.counter("notification_error", Tags.of(cashDto.login())).increment();
        throw new KafkaException(MESSAGE_NOT_DELIVERED_FORMAT.formatted(topic, e.getMessage()));
      }
    }
  }
}
