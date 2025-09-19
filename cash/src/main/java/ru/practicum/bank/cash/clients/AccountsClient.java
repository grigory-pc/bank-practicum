package ru.practicum.bank.cash.clients;

import reactor.core.publisher.Mono;
import ru.practicum.bank.cash.dto.CashChangeRequestDto;

/**
 * Класс для запросов в микросервис Accounts.
 */
public interface AccountsClient {
  /**
   * Запрос на снятие средств в микросервис Accounts.
   *
   * @param requestDto - объект с данными для изменения баланса.
   * @return объект mono.
   */
  Mono<Void> requestGetCash(CashChangeRequestDto requestDto);

  /**
   * Запрос на пополнение средств в микросервис Accounts.
   *
   * @param requestDto - объект с данными для изменения баланса.
   * @return объект mono.
   */
  Mono<Void> requestPutCash(CashChangeRequestDto requestDto);
}
