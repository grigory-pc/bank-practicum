package ru.practicum.bank.exchange.configs.clients;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client.exchange-generator")
public record ExchangeGeneratorClientProps(String baseUrl, int connectTimeoutMs,
                                           int responseTimeoutMs) {
}
