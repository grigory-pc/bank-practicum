package ru.practicum.bank.front.ui.configs.clients.transfer;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client.transfer")
public record TransferClientProps(String baseUrl, int connectTimeoutMs, int responseTimeoutMs) {
}
