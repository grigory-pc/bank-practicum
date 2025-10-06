package ru.practicum.bank.front.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

/**
 * DTO пользователя со всей информацией.
 */
@Builder
public record UserFullDto(@JsonProperty(value = "login", required = true) @NotBlank String login,
                          @JsonProperty(value = "password",
                                        required = true) @NotBlank String password,
                          @JsonProperty(value = "name", required = true) @NotBlank String name,
                          @JsonProperty(value = "birthdate",
                                        required = true) @NotNull LocalDate birthdate,
                          @JsonProperty(value = "accounts",
                                        required = true) @NotNull List<AccountsDto> accounts,
                          @JsonProperty(value = "currency",
                                        required = true) @NotNull List<CurrencyDto> currency,
                          @JsonProperty(value = "users",
                                        required = true) @NotNull List<UserShortDto> users,
                          @JsonProperty(value = "passwordErrors",
                                        required = true) @NotNull List<String> passwordErrors,
                          @JsonProperty(value = "userAccountsErrors",
                                        required = true) @NotNull List<String> userAccountsErrors,
                          @JsonProperty(value = "cashErrors",
                                        required = true) @NotNull List<String> cashErrors,
                          @JsonProperty(value = "transferErrors",
                                        required = true) @NotNull List<String> transferErrors,
                          @JsonProperty(value = "transferOtherErrors",
                                        required = true) @NotNull List<String> transferOtherErrors) {
}
