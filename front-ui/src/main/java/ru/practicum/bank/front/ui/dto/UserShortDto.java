package ru.practicum.bank.front.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * DTO пользователя.
 */
@Builder
public record UserShortDto(@JsonProperty(value = "login", required = true) @NotBlank String login,
                           @JsonProperty(value = "password", required = true) String password,
                           @JsonProperty(value = "role", required = true) String role) {
}
