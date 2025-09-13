package ru.practicum.bank.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record AccountsDto(@JsonProperty(value = "currency", required = true) @NotBlank List<CurrencyDto> currency,
                          @JsonProperty(value = "value", required = true) @NotBlank Double value,
                          @JsonProperty(value = "exists", required = true) Boolean exists) {
}