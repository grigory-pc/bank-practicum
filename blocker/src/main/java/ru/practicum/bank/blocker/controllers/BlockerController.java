package ru.practicum.bank.blocker.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для блокировки операций.
 */
@Slf4j
@RestController
@RequestMapping("/blocker")
@RequiredArgsConstructor
public class BlockerController {

  /**
   * Блокировка операции. Всегда возвращает true.
   */
  @GetMapping
  public Boolean requestBlock() {
    log.info("получен запрос на блокировку операции");

    System.out.println("Операция заблокирована");

    return true;
  }
}