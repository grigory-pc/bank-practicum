package ru.practicum.bank.exchange.generator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * DTO валюты.
 */
@Builder
public record RateDto(@JsonProperty(value = "title", required = true) String title,
                      @JsonProperty(value = "name", required = true) String name,
                      @JsonProperty(value = "value", required = true) Double value) {
}
