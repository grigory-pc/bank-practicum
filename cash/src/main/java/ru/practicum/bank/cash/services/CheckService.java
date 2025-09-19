package ru.practicum.bank.cash.services;

import ru.practicum.bank.cash.enums.Action;

/**
 * Сервис для проверок данных.
 */
public interface CheckService {

  /**
   * Метод для проверки и получения действия с балансом.
   *
   * @param action - действие с балансом.
   * @return действие из запроса;
   */
  Action checkAndGetAction(String action);
}