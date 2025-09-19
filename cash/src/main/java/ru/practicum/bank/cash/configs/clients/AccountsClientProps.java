package ru.practicum.bank.cash.configs.clients;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client.accounts")
public record AccountsClientProps(String baseUrl, int connectTimeoutMs, int responseTimeoutMs) {
}
