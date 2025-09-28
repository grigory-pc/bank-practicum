package ru.practicum.bank.cash.clients.accounts;

import reactor.core.publisher.Mono;
import ru.practicum.bank.cash.dto.AccountsDto;

/**
 * Класс для запросов в микросервис Accounts.
 */
public interface AccountsClient {

  /**
   * Метод для запроса аккаунта.
   * @param login - логин аккаунта.
   * @param currency - валюта аккаунта.
   * @return - объект аккаунта.
   */
  Mono<AccountsDto> requestGetAccount(String login, String currency);

  /**
   * Метод для обновления аккаунта.
   * @param accountsDto - список обновленных аккаунтов.
   */
  Mono<Void> updateAccount(AccountsDto accountsDto);
}
