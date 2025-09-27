package ru.practicum.bank.transfer.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.transfer.clients.accounts.AccountsClient;
import ru.practicum.bank.transfer.clients.exchange.ExchangeClient;
import ru.practicum.bank.transfer.dto.AccountsDto;
import ru.practicum.bank.transfer.dto.TransferDto;
import ru.practicum.bank.transfer.enums.Currency;
import ru.practicum.bank.transfer.enums.CurrencyExchange;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
  public static final boolean IS_EXISTS_TRUE = true;
  private final AccountsClient accountsClient;
  private final ExchangeClient exchangeClient;

  @Override
  public void transferToOtherAccount(TransferDto transferDto) {
    AccountsDto toAccount;
    AccountsDto updatedAccountTo;
    var fromCurrency = transferDto.fromCurrency();
    var toCurrency = transferDto.toCurrency();

    if (transferDto.login().equals(transferDto.toLogin()) && fromCurrency.equals(
        transferDto.toCurrency())) {
      log.warn("Получен запрос на перевод средств на свой аккаунт в той же валюте: {}",
               transferDto.fromCurrency());
      return;
    }

    var fromAccount = accountsClient.requestGetAccount(transferDto.login(),
                                                       fromCurrency).block();

    if (fromAccount.value() < transferDto.value()) {
      log.warn("На счёте недостаточно средств для снятия. На текущем счёте: {}",
               fromAccount.value());
      return;
    }

    toAccount = getToAccount(transferDto, toCurrency);

    var updatedAccountFrom = AccountsDto.builder()
                                        .currency(fromAccount.currency())
                                        .isExists(fromAccount.isExists())
                                        .value(fromAccount.value() - transferDto.value())
                                        .build();

    updatedAccountTo = updateToAccount(transferDto, toAccount);

    accountsClient.updateAccount(List.of(updatedAccountFrom, updatedAccountTo)).subscribe();
  }

  private AccountsDto updateToAccount(TransferDto transferDto, AccountsDto toAccount) {
    AccountsDto updatedAccountTo;
    if (transferDto.fromCurrency().equals(transferDto.toCurrency())) {
      updatedAccountTo = AccountsDto.builder()
                                    .currency(toAccount.currency())
                                    .isExists(IS_EXISTS_TRUE)
                                    .value(toAccount.value() + transferDto.value())
                                    .build();

    } else if (Currency.getValueOf(transferDto.fromCurrency()).equals(Currency.RUB)
               || Currency.getValueOf(transferDto.toCurrency()).equals(Currency.RUB)) {

      updatedAccountTo = getAccountsDtoOneOfCoupleRub(transferDto, transferDto.fromCurrency(),
                                                      transferDto.toCurrency(), toAccount);
    } else {
      updatedAccountTo = getAccountsDtoNoRubInCouple(transferDto, transferDto.fromCurrency(),
                                                     transferDto.toCurrency(), toAccount);
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

  private AccountsDto getAccountsDtoNoRubInCouple(TransferDto transferDto, String fromCurrency,
                                                  String toCurrency, AccountsDto toAccount) {
    AccountsDto updatedAccountTo;
    var currencyExchangeFrom = CurrencyExchange.getValueOf(
        String.format("%s_%s", fromCurrency.toUpperCase(), Currency.RUB.value));
    var currencyExchangeTo = CurrencyExchange.getValueOf(
        String.format("%s_%s", Currency.RUB.value, toCurrency.toUpperCase()));

    var rateFrom = exchangeClient.getTransferRate(currencyExchangeFrom);
    var rateTo = exchangeClient.getTransferRate(currencyExchangeTo);

    var valueTo = transferDto.value() * rateFrom.value() / rateTo.value();

    updatedAccountTo = AccountsDto.builder()
                                  .currency(toAccount.currency())
                                  .isExists(IS_EXISTS_TRUE)
                                  .value(toAccount.value() + valueTo)
                                  .build();
    return updatedAccountTo;
  }

  private AccountsDto getAccountsDtoOneOfCoupleRub(TransferDto transferDto, String fromCurrency,
                                                   String toCurrency, AccountsDto toAccount) {
    AccountsDto updatedAccountTo;
    var currencyExchange = CurrencyExchange.getValueOf(
        String.format("%s_%s", fromCurrency.toUpperCase(), toCurrency.toUpperCase()));

    var rate = exchangeClient.getTransferRate(currencyExchange);

    var valueCurrencyTo = transferDto.value() / rate.value();

    updatedAccountTo = AccountsDto.builder()
                                  .currency(toAccount.currency())
                                  .isExists(IS_EXISTS_TRUE)
                                  .value(toAccount.value() + valueCurrencyTo)
                                  .build();
    return updatedAccountTo;
  }
}