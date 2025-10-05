package ru.practicum.bank.exchange.generator.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client.exchange")
public record ExchangeClientProps(String baseUrl, int connectTimeoutMs, int responseTimeoutMs) {
}