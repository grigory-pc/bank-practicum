package ru.practicum.bank.accounts.services;

import java.time.LocalDate;

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
   * @param birthDate        - дата рождения.
   * @return результат проверки;
   */
  Boolean checkBirthdate(LocalDate birthDate);
}
