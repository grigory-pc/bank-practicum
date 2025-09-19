package ru.practicum.bank.cash.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * DTO для изменения баланса на счете в сервис accounts.
 */
@Builder
public record CashChangeRequestDto(@JsonProperty(value = "login", required = true) String login,
                                   @JsonProperty(value = "currency", required = true) String currency,
                                   @JsonProperty(value = "value", required = true) Integer value) {
}