package ru.practicum.bank.accounts.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.bank.accounts.dto.CashChangeRequestDto;
import ru.practicum.bank.accounts.exceptions.PasswordException;
import ru.practicum.bank.accounts.services.CashService;

@Slf4j
@RestController
@RequestMapping("/accounts/cash")
@RequiredArgsConstructor
public class CashController {
  private final CashService cashService;

  /**
   * Снятие средств с баланса пользователя.
   *
   * @param cashRequestDto - объект с данными для изменения баланса.
   */
  @PostMapping
  public void getCash(@Valid @RequestBody CashChangeRequestDto cashRequestDto)
      throws PasswordException {
    log.info("Получен запрос на снятие средств с баланса для пользователя: {}",
             cashRequestDto.login());

    cashService.getCashFromAccount(cashRequestDto);
  }
}