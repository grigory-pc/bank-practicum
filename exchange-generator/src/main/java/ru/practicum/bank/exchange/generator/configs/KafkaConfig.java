package ru.practicum.bank.exchange.generator.configs;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka-topics")
public record KafkaConfig(List<String> out) {
}
