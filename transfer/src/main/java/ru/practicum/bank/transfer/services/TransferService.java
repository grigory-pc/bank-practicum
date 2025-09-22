package ru.practicum.bank.transfer.services;

import ru.practicum.bank.transfer.dto.TransferDto;

/**
 * Сервис для перевода денег между своими счетами и перевода денег на счет другого
 * пользователя.
 */
public interface TransferService {

  /**
   * Перевод средств на свой аккаунт.
   * @param transferDto - объект с данными для перевода
   */
  void transferToSelfAccount(TransferDto transferDto);

  /**
   * Перевод средств на чужой аккаунт.
   * @param transferDto - объект с данными для перевода
   */
  void transferToOtherAccount(TransferDto transferDto);
}
