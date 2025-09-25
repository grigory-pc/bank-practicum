package ru.practicum.bank.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record AccountsDto(
    @JsonProperty(value = "currency", required = true) @NotNull CurrencyDto currency,
    @JsonProperty(value = "value", required = true) @NotNull Double value,
    @JsonProperty(value = "isExists", required = true) Boolean isExists) {
}