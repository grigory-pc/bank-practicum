package ru.practicum.bank.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Builder;

/**
 * DTO пользователя.
 */
@Builder
public record UserDto(@JsonProperty(value = "login", required = true) @NotBlank String login,
                      @JsonProperty(value = "password", required = true) @NotBlank String password,
                      @JsonProperty(value = "confirm_password",
                                    required = true) @NotBlank String confirm_password,
                      @JsonProperty(value = "name", required = true) @NotBlank String name,
                      @JsonProperty(value = "birthdate",
                                    required = true) @NotBlank LocalDate birthdate) {
}
