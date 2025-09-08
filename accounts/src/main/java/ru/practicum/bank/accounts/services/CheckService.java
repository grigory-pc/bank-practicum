package ru.practicum.bank.accounts.services;

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
}
