package ru.practicum.bank.transfer.services;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.transfer.clients.accounts.AccountsClient;
import ru.practicum.bank.transfer.clients.exchange.ExchangeClient;
import ru.practicum.bank.transfer.dto.AccountsDto;
import ru.practicum.bank.transfer.dto.TransferDto;
import ru.practicum.bank.transfer.enums.CurrencyExchange;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
  private final AccountsClient accountsClient;
  private final ExchangeClient exchangeClient;

  @Transactional
  @Override
  public void transferToOtherAccount(TransferDto transferDto) {
    AccountsDto toAccount;
    AccountsDto updatedAccountTo;
    var fromCurrency = transferDto.fromCurrency();
    var toCurrency = transferDto.toCurrency();

    var fromAccount = accountsClient.requestGetAccount(transferDto.login(),
                                                       fromCurrency).block();
    if (fromAccount.value() < transferDto.value()) {
      log.warn("На счёте недостаточно средств для снятия. На текущем счёте: {}",
               fromAccount.value());
      return;
    }

    if (transferDto.login().equals(transferDto.toLogin())) {
      log.info("Получен запрос на перевод средств на свой аккаунт");

      toAccount = accountsClient.requestGetAccount(transferDto.login(), toCurrency).block();
    } else {
      log.info("Получен запрос на перевод средств на чужой аккаунт");

      toAccount = accountsClient.requestGetAccount(transferDto.toLogin(), toCurrency).block();
    }

    var updatedAccountFrom = AccountsDto.builder()
                                        .currency(fromAccount.currency())
                                        .isExists(fromAccount.isExists())
                                        .value(fromAccount.value() - transferDto.value())
                                        .build();

    if (fromCurrency.equals(transferDto.toCurrency())) {
      updatedAccountTo = AccountsDto.builder()
                                    .currency(toAccount.currency())
                                    .isExists(toAccount.isExists())
                                    .value(toAccount.value() + transferDto.value())
                                    .build();
    } else {
      var currencyExchange = CurrencyExchange.getValueOf(
          String.format("%s_%s", fromCurrency.toUpperCase(), toCurrency.toUpperCase()));


      var rate = exchangeClient.getTransferRate(currencyExchange);
      var valueCurrencyTo = transferDto.value() / rate.value();

      updatedAccountTo = AccountsDto.builder()
                                    .currency(toAccount.currency())
                                    .isExists(toAccount.isExists())
                                    .value(toAccount.value() + valueCurrencyTo)
                                    .build();
    }

    accountsClient.updateAccount(List.of(updatedAccountFrom, updatedAccountTo)).subscribe();
  }
}