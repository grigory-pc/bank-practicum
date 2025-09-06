package ru.practicum.bank.front.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import ru.practicum.bank.front.ui.enums.Action;

/**
 * DTO для изменения баланса на счете.
 */
@Builder
public record CashDto(@JsonProperty(value = "login", required = true) String login,
                      @JsonProperty(value = "currency", required = true) String currency,
                      @JsonProperty(value = "value", required = true) Integer value,
                      @JsonProperty(value = "action", required = true) Action action) {
}