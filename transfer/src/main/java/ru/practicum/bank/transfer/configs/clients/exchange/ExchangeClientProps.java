package ru.practicum.bank.transfer.configs.clients.exchange;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client.exchange")
public record ExchangeClientProps(String baseUrl, int connectTimeoutMs, int responseTimeoutMs) {
}