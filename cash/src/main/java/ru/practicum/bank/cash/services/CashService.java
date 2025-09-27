package ru.practicum.bank.cash.services;

import ru.practicum.bank.cash.dto.CashDto;

/**
 * Сервис для операций на счёте.
 */
public interface CashService {

  /**
   * Метод для обработки запроса операции на счёте.
   * @param cashDto - данные для операций на счёте.
   */
  void requestCashOperation(CashDto cashDto);
}
