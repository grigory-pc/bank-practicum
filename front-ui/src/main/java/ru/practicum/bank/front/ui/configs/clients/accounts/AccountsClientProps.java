package ru.practicum.bank.front.ui.configs.clients.accounts;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client.accounts")
public record AccountsClientProps(String baseUrl, int connectTimeoutMs, int responseTimeoutMs) {
}
