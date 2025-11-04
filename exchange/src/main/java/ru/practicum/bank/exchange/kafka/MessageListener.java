package ru.practicum.bank.exchange.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import ru.practicum.bank.exchange.configs.kafka.KafkaConfig;
import ru.practicum.bank.exchange.dto.RateDto;
import ru.practicum.bank.exchange.services.RateService;


/**
 * Класс, отвечающий за прослушивание сообщений кафки.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageListener {
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final String INFO_GET_MESSAGE_FROM_TOPIC_FORMAT
      = "Из топика: '{}' получено сообщение на обработку с оффсетом - '{}' и партицией '{}'";
  private final KafkaConfig config;
  private final RateService rateService;

  /**
   * Прослушивает входные топики на наличие данных, при присутствии данных - считывает и
   * обрабатывает.
   *
   * @param kafkaRecord - входящий рекорд данных.
   * @param ack - позволяет настраивать необходимость считать сообщение успешно записанным.
   */

  @KafkaListener(id = "${spring.kafka.listener.client-id}", topics = "#{config.incomingTopics()}")
  public void listenAsByteArray(ConsumerRecord<String, String> kafkaRecord, Acknowledgment ack) {
    log.info(INFO_GET_MESSAGE_FROM_TOPIC_FORMAT, kafkaRecord.topic(), kafkaRecord.offset(),
             kafkaRecord.partition());
    try {
      handleRecord(kafkaRecord);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    ack.acknowledge();
  }

  private void handleRecord(ConsumerRecord<String, String> kafkaRecord)
      throws JsonProcessingException {
    var rates = MAPPER.readValue(kafkaRecord.value(), new TypeReference<List<RateDto>>() {
    });

    log.info("Из кафки получили сообщение для обновления курса валют в виде списка размером: {}",
             rates.size());

    rateService.updateRates(rates);
  }
}