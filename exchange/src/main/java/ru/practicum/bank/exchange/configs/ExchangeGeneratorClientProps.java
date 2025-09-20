package ru.practicum.bank.exchange.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client.exchange-generator")
public record ExchangeGeneratorClientProps(String baseUrl, int connectTimeoutMs,
                                           int responseTimeoutMs) {
}
