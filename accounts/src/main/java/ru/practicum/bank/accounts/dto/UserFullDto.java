package ru.practicum.bank.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * DTO пользователя со всей информацией.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserFullDto {
  @JsonProperty(value = "login", required = true)
  @NotBlank
  private String login;

  @JsonProperty(value = "password", required = true)
  @NotBlank
  private String password;

  @JsonProperty(value = "name", required = true)
  @NotBlank String name;

  @JsonProperty(value = "birthdate", required = true)
  @NotNull
  LocalDate birthdate;

  @JsonProperty(value = "accounts", required = true)
  List<AccountsDto> accounts;

  @JsonProperty(value = "currency", required = true)
  List<CurrencyDto> currency;

  @JsonProperty(value = "users", required = true)
  List<UserShortDto> users;

  @JsonProperty(value = "passwordErrors", required = true)
  List<String> passwordErrors;

  @JsonProperty(value = "userAccountsErrors", required = true)
  List<String> userAccountsErrors;

  @JsonProperty(value = "cashErrors", required = true)
  List<String> cashErrors;

  @JsonProperty(value = "transferErrors", required = true)
  List<String> transferErrors;

  @JsonProperty(value = "transferOtherErrors", required = true)
  List<String> transferOtherErrors;
}