package ru.practicum.bank.transfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountRequestDto(@JsonProperty(value = "login", required = true) String login,
                                @JsonProperty(value = "currency",
                                              required = true) String currency) {
}
