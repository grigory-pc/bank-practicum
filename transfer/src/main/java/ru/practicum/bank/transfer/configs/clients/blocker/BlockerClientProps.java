package ru.practicum.bank.transfer.configs.clients.blocker;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client.blocker")
public record BlockerClientProps(String baseUrl, int connectTimeoutMs, int responseTimeoutMs) {
}
