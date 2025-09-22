package ru.practicum.bank.transfer.clients.accounts;

import reactor.core.publisher.Mono;
import ru.practicum.bank.transfer.dto.AccountRequestDto;
import ru.practicum.bank.transfer.dto.AccountsDto;

/**
 * Класс для запросов в микросервис Accounts.
 */
public interface AccountsClient {

  /**
   * Метод для запроса аккаунта.
   * @param accountRequest - данные с логином и валютой для запроса аккаунта.
   * @return - объект аккаунта.
   */
  Mono<AccountsDto> requestGetAccount(AccountRequestDto accountRequest);

  /**
   * Метод для обновления аккаунта.
   * @param accountsDto - обновленные данные аккаунта.
   */
  Mono<Void> updateAccount(AccountsDto accountsDto);
}