package ru.practicum.bank.transfer.configs.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("oauth2")
public record OAuth2ConfigProps(String clientRegistrationId, String principal) {
}
