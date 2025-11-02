package ru.practicum.bank.notifications.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Хранит в себе входящие топики для KafkaListener.
 *
 * @param in - список входящих топиков.
 */
@ConfigurationProperties(prefix = "kafka-topics")
public record KafkaConfig(List<String> in) {

  /**
   * Метод для получения массива входящих топиков.
   *
   * @return массив топиков для подписки.
   */
  public String[] incomingTopics() {
    return in.toArray(String[]::new);
  }
}