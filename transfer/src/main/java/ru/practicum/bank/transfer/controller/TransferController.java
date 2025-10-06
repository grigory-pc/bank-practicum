package ru.practicum.bank.transfer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.bank.transfer.dto.TransferDto;
import ru.practicum.bank.transfer.services.TransferService;

/**
 * Контроллер для перевода денежных средств.
 */
@Slf4j
@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {
  private final TransferService transferService;

  /**
   * Перевод средств.
   *
   * @param transferDto - объект с данными для перевода
   */
  @PostMapping
  public void transferCash(@Valid @RequestBody TransferDto transferDto) {
    log.info("получен запрос на перевод средств");

    transferService.transferToOtherAccount(transferDto);
  }
}