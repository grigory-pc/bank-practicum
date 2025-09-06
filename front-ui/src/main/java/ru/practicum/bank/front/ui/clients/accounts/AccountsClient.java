package ru.practicum.bank.front.ui.clients.accounts;

import reactor.core.publisher.Mono;
import ru.practicum.bank.front.ui.dto.AccountDto;

/**
 * Класс для запросов в микросервис Accounts.
 */
public interface AccountsClient {

  /**
   * Запрос в микросервис Accounts.
   */
  Mono<Void> requestAccount(String path, AccountDto account);
}
