package ru.practicum.bank.front.ui.configs.cash;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client.cash")
public record CashClientProps(String baseUrl, int connectTimeoutMs, int responseTimeoutMs) {
}
