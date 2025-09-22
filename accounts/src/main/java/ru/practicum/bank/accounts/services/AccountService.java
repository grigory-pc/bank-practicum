package ru.practicum.bank.accounts.services;

import ru.practicum.bank.accounts.dto.AccountRequestDto;
import ru.practicum.bank.accounts.dto.AccountsDto;

/**
 * Сервис для работы с аккаунтами.
 */
public interface AccountService {

  /**
   * Метод для получения объекта аккаунта.
   *
   * @param accountRequest - данные с логином и валютой для запроса аккаунта.
   * @return объект аккаунта.
   */
  AccountsDto getAccount(AccountRequestDto accountRequest);

  /**
   * Метод для обновления объекта аккаунта.
   *
   * @param accountsDto - объект с обновленными данными аккаунта.
   */
  void updateAccount(AccountsDto accountsDto);
}
