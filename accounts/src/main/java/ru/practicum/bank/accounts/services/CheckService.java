package ru.practicum.bank.accounts.services;

import java.time.LocalDate;
import ru.practicum.bank.accounts.entity.Currency;
import ru.practicum.bank.accounts.exceptions.CurrencyException;

/**
 * Сервис для проверок данных.
 */
public interface CheckService {

  /**
   * Метод для проверки совпадения пароля.
   *
   * @param password        - пароль.
   * @param confirmPassword - повторный ввод пароля
   * @return результат проверки;
   */
  Boolean checkPassword(String password, String confirmPassword);

  /**
   * Метод для проверки даты рождения.
   *
   * @param birthDate - дата рождения.
   * @return результат проверки;
   */
  Boolean checkBirthdate(LocalDate birthDate);

  /**
   * Метод для проверки поддержки валюты для операции.
   *
   * @param currency - тип валюты из запроса для операции с балансом или переводом.
   * @return валюту из запроса;
   * @throws CurrencyException - исключение в случае неподдерживаемого типа валюты.
   */
  Currency checkAndGetCurrency(String currency) throws CurrencyException;
}