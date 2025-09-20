package ru.practicum.bank.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record AccountsDto(
    @JsonProperty(value = "currency", required = true) @NotBlank CurrencyDto currency,
    @JsonProperty(value = "value", required = true) @NotBlank Double value,
    @JsonProperty(value = "isExists", required = true) Boolean isExists) {
}