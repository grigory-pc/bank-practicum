package ru.practicum.bank.transfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AccountsDto(
    @JsonProperty(value = "currency", required = true) @NotNull CurrencyDto currency,
    @JsonProperty(value = "value", required = true) @NotNull Double value,
    @JsonProperty(value = "isExists", required = true) Boolean isExists) {
}