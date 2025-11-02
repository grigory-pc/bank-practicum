package ru.practicum.bank.notifications.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import ru.practicum.bank.notifications.config.KafkaConfig;
import ru.practicum.bank.notifications.dto.CashDto;
import ru.practicum.bank.notifications.dto.TransferDto;
import ru.practicum.bank.notifications.dto.UserNotifyDto;
import ru.practicum.bank.notifications.enums.NotificationType;
import ru.practicum.bank.notifications.services.NotificationsService;


/**
 * Класс, отвечающий за прослушивание сообщений кафки.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageListener {
  private static final String USER_NOTIFICATION = "user_notification";
  private static final String CASH_NOTIFICATION = "cash_notification";
  private static final String TRANSFER_NOTIFICATION = "transfer_notification_";

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final String INFO_GET_MESSAGE_FROM_TOPIC_FORMAT
      = "Из топика: '{}' получено сообщение на обработку с оффсетом - '{}' и партицией '{}'";
  private final KafkaConfig config;
  private final NotificationsService notificationsService;

  /**
   * Прослушивает входные топики на наличие данных, при присутсвии данных - считывает и
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

    switch (classifyNotification(kafkaRecord.key())) {
      case USER -> {
        var userNotification = MAPPER.readValue(kafkaRecord.value(), UserNotifyDto.class);

        log.info("Из кафки получили сообщение для оповещения пользователя: {}",
                 userNotification.getName());

        notificationsService.userNotification(userNotification);
      }
      case CASH -> {
        var cashNotification = MAPPER.readValue(kafkaRecord.value(), CashDto.class);

        log.info("Из кафки получили сообщение для оповещения пользователя {} по операции: {}",
                 cashNotification.login(), cashNotification.action());

        notificationsService.cashNotification(cashNotification);
      }
      case TRANSFER -> {
        var transferNotification = MAPPER.readValue(kafkaRecord.value(), TransferDto.class);

        log.info(
            "Из кафки получили сообщение для оповещения пользователя {} по переводу средств пользователю: {}",
            transferNotification.login(), transferNotification.toLogin());

        notificationsService.transferNotification(transferNotification);
      }
      case UNKNOWN -> log.warn("Неизвестный тип уведомления. Сообщение не будет отправлено");
    }
  }

  public static NotificationType classifyNotification(String kafkaRecordKey) {
    if (kafkaRecordKey.contains(USER_NOTIFICATION)) {
      return NotificationType.USER;
    }
    if (kafkaRecordKey.contains(CASH_NOTIFICATION)) {
      return NotificationType.CASH;
    }
    if (kafkaRecordKey.contains(TRANSFER_NOTIFICATION)) {
      return NotificationType.TRANSFER;
    }
    return NotificationType.UNKNOWN;
  }
}