package ru.practicum.bank.cash.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.bank.cash.clients.AccountsClient;
import ru.practicum.bank.cash.dto.CashChangeRequestDto;
import ru.practicum.bank.cash.dto.CashDto;
import ru.practicum.bank.cash.enums.Action;
import ru.practicum.bank.cash.services.CheckService;

/**
 * Контроллер для обработки запросов на операции с балансом на счете.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cash")
public class CashController {
  private final CheckService checkService;
  private final AccountsClient accountsClient;


  /**
   * Контроллер для обработки запросов на снятие или зачисление денег на счет.
   *
   * @param cashDto - объект с данными дял изменения баланса.).
   */
  @PostMapping
  public void requestCashOperation(@Valid @RequestBody CashDto cashDto) {
    log.info("получен запрос на операцию с наличными: {} для пользователя: {}", cashDto.action(),
             cashDto.login());

    Action action = checkService.checkAndGetAction(cashDto.action());

    var cashChangeRequestDto = CashChangeRequestDto.builder()
                                                   .login(cashDto.login())
                                                   .currency(cashDto.currency())
                                                   .value(cashDto.value())
                                                   .build();

    switch (action) {
      case GET -> accountsClient.requestGetCash(cashChangeRequestDto).block();
      case PUT -> accountsClient.requestPutCash(cashChangeRequestDto).block();
    }
  }
}