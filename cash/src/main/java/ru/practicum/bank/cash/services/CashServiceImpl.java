package ru.practicum.bank.cash.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.cash.clients.accounts.AccountsClient;
import ru.practicum.bank.cash.clients.blocker.BlockerClient;
import ru.practicum.bank.cash.clients.notifications.NotificationsClient;
import ru.practicum.bank.cash.dto.AccountsDto;
import ru.practicum.bank.cash.dto.CashDto;
import ru.practicum.bank.cash.enums.Action;

@Slf4j
@Service
@RequiredArgsConstructor
public class CashServiceImpl implements CashService {
  public static final boolean IS_CAN_GET = true;
  private final AccountsClient accountsClient;
  private final NotificationsClient notificationsClient;
  private final BlockerClient blockerClient;

  @Override
  public void requestCashOperation(CashDto cashDto) {
    var action = Action.getValueOf(cashDto.action());

    var account = accountsClient.requestGetAccount(cashDto.login(), cashDto.currency()).block();
    log.info("Счёт пользователя: {} для валюты: {} найден", cashDto.currency(), cashDto.login());

    switch (action) {
      case GET -> {
        boolean isCanAccountForGet = isCanAccountForGet(cashDto, account);
        if (Boolean.FALSE.equals(isCanAccountForGet)) {
          return;
        }
        accountsClient.updateAccount(requestGetCash(cashDto, account)).block();
      }
      case PUT -> accountsClient.updateAccount(requestPutCash(cashDto, account)).block();
    }

    notificationsClient.requestCashNotifications(cashDto).block();
  }

  private AccountsDto requestGetCash(CashDto cashDto, AccountsDto account) {
    var updatedValue = account.value() - cashDto.value();

    return AccountsDto.builder()
                      .id(account.id())
                      .userId(account.userId())
                      .currency(account.currency())
                      .isExists(account.isExists())
                      .value(updatedValue)
                      .build();
  }

  private AccountsDto requestPutCash(CashDto cashDto, AccountsDto account) {
    var updatedValue = account.value() + cashDto.value();

    return AccountsDto.builder()
                      .id(account.id())
                      .userId(account.userId())
                      .currency(account.currency())
                      .isExists(IS_CAN_GET)
                      .value(updatedValue)
                      .build();
  }

  private boolean isCanAccountForGet(CashDto cashDto, AccountsDto account) {
    if (Boolean.FALSE.equals(account.isExists())) {
      log.warn("Аккаунт пользователя не активен");

      var isOperationBlocked = blockerClient.requestBlockOperation().block();

      return Boolean.FALSE.equals(isOperationBlocked);
    } else if (account.value() < cashDto.value()) {
      log.warn("На счёте недостаточно средств для снятия. На текущем счёте: {}",
               account.value());

      var isOperationBlocked = blockerClient.requestBlockOperation().block();

      return Boolean.FALSE.equals(isOperationBlocked);
    } else {
      return IS_CAN_GET;
    }
  }
}