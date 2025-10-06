package ru.practicum.bank.accounts.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.bank.accounts.dto.AccountsDto;
import ru.practicum.bank.accounts.services.AccountService;

/**
 * Контроллер для обработки запросов на манипуляции с аккаунтом пользователя.
 */
@Slf4j
@RestController
@RequestMapping("/accounts/account")
@RequiredArgsConstructor
public class AccountController {
  private final AccountService accountService;


  /**
   * Получение информации об аккаунте пользователя.
   *
   * @param login - логин аккаунта.
   * @param currency - валюта аккаунта.
   */
  @GetMapping("/{login}/{currency}")
  public AccountsDto getAccount(@PathVariable String login,
                                @PathVariable String currency) {
    log.info("Получен запрос на получение аккаунта пользователя: {}", login);

    return accountService.getAccount(login, currency);
  }

  /**
   * Обновление данных аккаунта.
   *
   * @param accountsDto - обновленный объект аккаунта.
   */
  @PatchMapping
  public void updateAccount(@Valid @RequestBody AccountsDto accountsDto) {
    log.info("Получен запрос на обновление аккаунта");

    accountService.updateAccount(accountsDto);
  }
}