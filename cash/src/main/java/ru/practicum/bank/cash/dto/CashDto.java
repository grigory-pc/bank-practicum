package ru.practicum.bank.cash.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * DTO для изменения баланса на счете.
 */
@Builder
public record CashDto(@JsonProperty(value = "login", required = true) @NotBlank String login,
                      @JsonProperty(value = "currency", required = true) @NotBlank String currency,
                      @JsonProperty(value = "value", required = true) @NotNull Integer value,
                      @JsonProperty(value = "action", required = true) @NotBlank String action) {
}