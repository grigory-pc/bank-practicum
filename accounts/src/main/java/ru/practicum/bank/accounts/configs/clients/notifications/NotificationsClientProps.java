package ru.practicum.bank.accounts.configs.clients.notifications;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client.notifications")
public record NotificationsClientProps(String baseUrl, int connectTimeoutMs,
                                       int responseTimeoutMs) {
}
