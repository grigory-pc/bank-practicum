package ru.practicum.bank.notifications.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.bank.notifications.dto.CashDto;
import ru.practicum.bank.notifications.dto.TransferDto;
import ru.practicum.bank.notifications.services.NotificationsService;

/**
 * Контроллер для уведомлений.
 */
@Slf4j
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationsController {
  private final NotificationsService notificationsService;

  /**
   * Уведомление о переводе средств на другой счет.
   * @param transferDto - данные по переводу средств.
   */
  @PostMapping("/transfer")
  public void transferNotifications(@Valid @RequestBody TransferDto transferDto) {
    log.info("получен запрос на перевод средств");

    notificationsService.transferNotification(transferDto);
  }

  /**
   * Уведомление о действиях на счёте.
   * @param cashDto - данные по действиям на счёте.
   */
  @PostMapping("/cash")
  public void cashNotifications(@Valid @RequestBody CashDto cashDto) {
    log.info("получен запрос на перевод средств");

    notificationsService.cashNotification(cashDto);
  }
}