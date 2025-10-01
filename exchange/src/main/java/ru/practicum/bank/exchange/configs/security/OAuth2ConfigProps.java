package ru.practicum.bank.exchange.configs.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("oauth2")
public record OAuth2ConfigProps(String clientRegistrationId, String principal) {
}
