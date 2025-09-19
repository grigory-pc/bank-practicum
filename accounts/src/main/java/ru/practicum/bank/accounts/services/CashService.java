package ru.practicum.bank.accounts.services;

import ru.practicum.bank.accounts.dto.CashChangeRequestDto;

public interface CashService {

  /**
   * Метод для снятия средств с баланса аккаунта/счета пользователя.
   * @param cashRequestDto - объект с данными для изменения баланса.
   */
  void getCashFromAccount(CashChangeRequestDto cashRequestDto);

  /**
   * Метод для снятия пополнение баланса аккаунта/счета пользователя.
   * @param cashRequestDto - объект с данными для изменения баланса.
   */
  void putCashToAccount(CashChangeRequestDto cashRequestDto);
}
