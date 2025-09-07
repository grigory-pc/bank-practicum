package ru.practicum.bank.front.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * DTO для изменения баланса на счете.
 */
@Builder
public record TransferDto(@JsonProperty(value = "login", required = true) String login,
                          @JsonProperty(value = "from_currency", required = true) String fromCurrency,
                          @JsonProperty(value = "to_currency", required = true) String toCurrency,
                          @JsonProperty(value = "value", required = true) Integer value,
                          @JsonProperty(value = "to_login", required = true) String toLogin) {
}