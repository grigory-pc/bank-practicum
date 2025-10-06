package ru.practicum.bank.transfer.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.transfer.clients.accounts.AccountsClient;
import ru.practicum.bank.transfer.clients.blocker.BlockerClient;
import ru.practicum.bank.transfer.clients.exchange.ExchangeClient;
import ru.practicum.bank.transfer.clients.notifications.NotificationsClient;
import ru.practicum.bank.transfer.dto.AccountsDto;
import ru.practicum.bank.transfer.dto.TransferDto;
import ru.practicum.bank.transfer.enums.Currency;
import ru.practicum.bank.transfer.enums.CurrencyExchange;
import ru.practicum.bank.transfer.exceptions.BlockerException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
  public static final boolean IS_EXISTS_TRUE = true;
  private final AccountsClient accountsClient;
  private final ExchangeClient exchangeClient;
  private final NotificationsClient notificationsClient;
  private final BlockerClient blockerClient;

  @Override
  public void transferToOtherAccount(TransferDto transferDto) {
    AccountsDto toAccount;
    AccountsDto updatedAccountTo;

    if (transferDto.login().equals(transferDto.toLogin()) && transferDto.fromCurrency().equals(
        transferDto.toCurrency())) {
      log.warn("Получен запрос на перевод средств на свой аккаунт в той же валюте: {}",
               transferDto.fromCurrency());

      blockOperation();

      return;
    }

    var fromAccount = accountsClient.requestGetAccount(transferDto.login(),
                                                       transferDto.fromCurrency()).block();

    if (fromAccount.value() < transferDto.value()) {
      log.warn("На счёте недостаточно средств для снятия. На текущем счёте: {}",
               fromAccount.value());

      blockOperation();

      return;
    }

    toAccount = getToAccount(transferDto, transferDto.toCurrency());

    var updatedAccountFrom = AccountsDto.builder()
                                        .id(fromAccount.id())
                                        .userId(fromAccount.userId())
                                        .currency(fromAccount.currency())
                                        .isExists(fromAccount.isExists())
                                        .value(fromAccount.value() - transferDto.value())
                                        .build();

    updatedAccountTo = updateToAccount(transferDto, toAccount);

    accountsClient.updateAccount(List.of(updatedAccountFrom, updatedAccountTo)).subscribe();

    notificationsClient.requestTransferNotifications(transferDto).block();
  }

  private void blockOperation() {
    log.info("Отправляется запрос в сервис блокировки");

    var isOperationBlocked = blockerClient.requestBlockOperation().block();

    if (Boolean.TRUE.equals(isOperationBlocked)) {
      log.info("Операция заблокирована");
    } else {
      throw new BlockerException("Некорректный ответ от сервиса блокировки");
    }
  }

  private AccountsDto updateToAccount(TransferDto transferDto, AccountsDto toAccount) {
    AccountsDto updatedAccountTo;
    if (transferDto.fromCurrency().equals(transferDto.toCurrency())) {
      updatedAccountTo = AccountsDto.builder()
                                    .id(toAccount.id())
                                    .userId(toAccount.userId())
                                    .currency(toAccount.currency())
                                    .isExists(IS_EXISTS_TRUE)
                                    .value(toAccount.value() + transferDto.value())
                                    .build();

    } else if (Currency.getValueOf(transferDto.fromCurrency()).equals(Currency.RUB)
               || Currency.getValueOf(transferDto.toCurrency()).equals(Currency.RUB)) {

      updatedAccountTo = getAccountsDtoOneOfCoupleRub(transferDto, toAccount);
    } else {
      updatedAccountTo = getAccountsDtoNoRubInCouple(transferDto, toAccount);
    }
    return updatedAccountTo;
  }

  private AccountsDto getToAccount(TransferDto transferDto, String toCurrency) {
    AccountsDto toAccount;
    if (transferDto.login().equals(transferDto.toLogin())) {
      log.info("Получен запрос на перевод средств на свой аккаунт");

      toAccount = accountsClient.requestGetAccount(transferDto.login(), toCurrency).block();
    } else {
      log.info("Получен запрос на перевод средств на чужой аккаунт");

      toAccount = accountsClient.requestGetAccount(transferDto.toLogin(), toCurrency).block();
    }
    return toAccount;
  }

  private AccountsDto getAccountsDtoNoRubInCouple(TransferDto transferDto, AccountsDto toAccount) {
    AccountsDto updatedAccountTo;
    var currencyExchangeFrom = CurrencyExchange.getValueOf(
        String.format("%s_%s", transferDto.fromCurrency().toUpperCase(), Currency.RUB.value));
    var currencyExchangeTo = CurrencyExchange.getValueOf(
        String.format("%s_%s", Currency.RUB.value, transferDto.toCurrency().toUpperCase()));

    var rateFrom = exchangeClient.getTransferRate(currencyExchangeFrom);
    var rateTo = exchangeClient.getTransferRate(currencyExchangeTo);

    var valueTo = transferDto.value() * rateFrom.value() * rateTo.value();

    updatedAccountTo = AccountsDto.builder()
                                  .id(toAccount.id())
                                  .userId(toAccount.userId())
                                  .currency(toAccount.currency())
                                  .isExists(IS_EXISTS_TRUE)
                                  .value(toAccount.value() + valueTo)
                                  .build();
    return updatedAccountTo;
  }

  private AccountsDto getAccountsDtoOneOfCoupleRub(TransferDto transferDto, AccountsDto toAccount) {
    AccountsDto updatedAccountTo;
    var currencyExchange = CurrencyExchange.getValueOf(
        String.format("%s_%s", transferDto.fromCurrency().toUpperCase(),
                      transferDto.toCurrency().toUpperCase()));

    var rate = exchangeClient.getTransferRate(currencyExchange);

    var valueCurrencyTo = transferDto.value() * rate.value();

    updatedAccountTo = AccountsDto.builder()
                                  .id(toAccount.id())
                                  .userId(toAccount.userId())
                                  .currency(toAccount.currency())
                                  .isExists(IS_EXISTS_TRUE)
                                  .value(toAccount.value() + valueCurrencyTo)
                                  .build();
    return updatedAccountTo;
  }
}