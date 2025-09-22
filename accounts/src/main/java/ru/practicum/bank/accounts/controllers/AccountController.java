package ru.practicum.bank.accounts.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.bank.accounts.dto.AccountRequestDto;
import ru.practicum.bank.accounts.dto.AccountsDto;
import ru.practicum.bank.accounts.services.UserService;

/**
 * Контроллер для обработки запросов на манипуляции с аккаунтом пользователя.
 */
@Slf4j
@RestController
@RequestMapping("/accounts/account")
@RequiredArgsConstructor
public class AccountController {
  private final UserService userService;


  /**
   * Получение информации об аккаунте пользователя.
   *
   * @param accountRequest - данные с логином и валютой для запроса аккаунта.
   */
  @GetMapping
  public AccountsDto getAccount(@Valid @RequestBody AccountRequestDto accountRequest) {
    log.info("Получен запрос на получение аккаунта пользователя: {}", accountRequest.login());

    return userService.getUserFullByLogin(login);
  }

  /**
   * Обновление данных аккаунта.
   *
   * @param accountsDto - обновленный объект аккаунта.
   */
  @PatchMapping
  public void updateAccount(@Valid @RequestBody AccountsDto accountsDto) {
    log.info("Получен запрос на обновление аккаунта");

    userService.updateUserData(userDto);
  }
}