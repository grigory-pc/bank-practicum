package ru.practicum.bank.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Builder;

/**
 * DTO пользователя.
 */
@Builder
public record UserDto(@JsonProperty(value = "login", required = true) String login,
                      @JsonProperty(value = "password", required = true) String password,
                      @JsonProperty(value = "confirm_password", required = true) String confirm_password,
                      @JsonProperty(value = "name", required = true) String name,
                      @JsonProperty(value = "birthdate", required = true) LocalDate birthdate) {
}
