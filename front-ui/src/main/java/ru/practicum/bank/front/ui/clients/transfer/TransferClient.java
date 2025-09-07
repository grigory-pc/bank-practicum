package ru.practicum.bank.front.ui.clients.transfer;

import reactor.core.publisher.Mono;
import ru.practicum.bank.front.ui.dto.TransferDto;

/**
 * Класс для запросов в микросервис transfer.
 */
public interface TransferClient {

  /**
   * Запрос на перевод средств в микросервис transfer.
   */
  Mono<Void> requestTransfer(TransferDto transferDto);
}
