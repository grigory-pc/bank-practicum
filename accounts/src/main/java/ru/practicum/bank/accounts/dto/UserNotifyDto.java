package ru.practicum.bank.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * DTO пользователя с информацией для уведомления.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserNotifyDto {
  @JsonProperty(value = "login", required = true)
  @NotBlank
  private String login;

  @JsonProperty(value = "name", required = true)
  @NotBlank String name;

  @JsonProperty(value = "accounts", required = true)
  List<AccountsDto> accounts;
}