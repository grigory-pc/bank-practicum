package ru.practicum.bank.notifications.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KafkaConfig.class)
public class KafkaConfigurationBinding {
  private final KafkaConfig config;

  @Autowired
  public KafkaConfigurationBinding(KafkaConfig kafkaConfig) {
    this.config = kafkaConfig;
  }

  @Bean
  public KafkaConfig config() {
    return config;
  }
}
