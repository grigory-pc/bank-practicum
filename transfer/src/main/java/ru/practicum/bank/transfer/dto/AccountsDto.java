package ru.practicum.bank.transfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AccountsDto(
    @JsonProperty(value = "currency", required = true) @NotBlank CurrencyDto currency,
    @JsonProperty(value = "value", required = true) @NotBlank Double value,
    @JsonProperty(value = "isExists", required = true) Boolean isExists) {
}