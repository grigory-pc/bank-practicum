package ru.practicum.bank.cash.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.bank.cash.dto.CashDto;
import ru.practicum.bank.cash.services.CashService;

/**
 * Контроллер для обработки запросов на операции с балансом на счете.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cash")
public class CashController {
  private final CashService cashService;

  /**
   * Контроллер для обработки запросов на снятие или зачисление денег на счет.
   *
   * @param cashDto - объект с данными дял изменения баланса.).
   */
  @PostMapping
  public void requestCashOperation(@Valid @RequestBody CashDto cashDto) {
    log.info("получен запрос на операцию с наличными: {} для пользователя: {}", cashDto.action(),
             cashDto.login());

    cashService.requestCashOperation(cashDto);
  }
}