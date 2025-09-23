package ru.practicum.bank.accounts.services;

import ru.practicum.bank.accounts.dto.AccountsDto;

/**
 * Сервис для работы с аккаунтами.
 */
public interface AccountService {

  /**
   * Метод для получения объекта аккаунта.
   *
   * @param login - логин аккаунта.
   * @param currency - валюта аккаунта.
   * @return объект аккаунта.
   */
  AccountsDto getAccount(String login, String currency);

  /**
   * Метод для обновления объекта аккаунта.
   *
   * @param accountsDto - объект с обновленными данными аккаунта.
   */
  void updateAccount(AccountsDto accountsDto);
}
