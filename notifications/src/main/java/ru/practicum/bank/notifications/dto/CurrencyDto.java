package ru.practicum.bank.notifications.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record CurrencyDto(@JsonProperty(value = "title", required = true) @NotBlank String title,
                          @JsonProperty(value = "name", required = true) @NotBlank String name) {
}
