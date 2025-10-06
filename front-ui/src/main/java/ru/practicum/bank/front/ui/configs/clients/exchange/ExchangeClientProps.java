package ru.practicum.bank.front.ui.configs.clients.exchange;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client.exchange")
public record ExchangeClientProps(String baseUrl, int connectTimeoutMs, int responseTimeoutMs) {
}