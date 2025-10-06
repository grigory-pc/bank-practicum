package ru.practicum.bank.transfer.clients.accounts;

import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.bank.transfer.dto.AccountsDto;

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
  Mono<AccountsDto> requestGetAccount(String login,  String currency);

  /**
   * Метод для обновления аккаунта.
   * @param accountsDto - список обновленных аккаунтов.
   */
  Flux<Void> updateAccount(List<AccountsDto> accountsDto);
}