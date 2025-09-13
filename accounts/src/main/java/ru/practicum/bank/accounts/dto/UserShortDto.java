package ru.practicum.bank.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * DTO пользователя с краткой информацией.
 */
@Builder
public record UserShortDto(@JsonProperty(value = "login", required = true) @NotBlank String login,
                           @JsonProperty(value = "name", required = true) String name) {
}
