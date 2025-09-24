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

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
  private final AccountsClient accountsClient;
  private final ExchangeClient exchangeClient;

  @Transactional
  @Override
  public void transferToSelfAccount(TransferDto transferDto) {
    var fromCurrency = transferDto.fromCurrency();
    var toCurrency = transferDto.toCurrency();


    var fromAccount = accountsClient.requestGetAccount(transferDto.login(),
                                                       fromCurrency).block();
    var toAccount = accountsClient.requestGetAccount(transferDto.login(), toCurrency)
                                  .block();

    if (fromCurrency.equals(transferDto.toCurrency())) {
      updateSelfAccoountSameCurrency(transferDto, fromAccount, toAccount);
    } else {
      var rates = exchangeClient.getTransferRates(fromCurrency, toCurrency);

      var fromValueRub = rates.stream()
                              .filter(rate -> rate.name().equals(fromCurrency))
                              .map(rate -> Double.valueOf(rate.value() * transferDto.value()))
                              .findFirst()
                              .orElse(0.00);

      var toValueRub = rates.stream()
                              .filter(rate -> rate.name().equals(toCurrency))
                              .map(rate -> Double.valueOf(rate.value() * transferDto.value()))
                              .findFirst()
                              .orElse(0.00);


    }
  }

  @Override
  public void transferToOtherAccount(TransferDto transferDto) {

  }

  private void updateSelfAccoountSameCurrency(TransferDto transferDto, AccountsDto fromAccount,
                                              AccountsDto toAccount) {
    if (fromAccount.value() > transferDto.value()) {
      var updatedAccountFrom = AccountsDto.builder()
                                          .currency(fromAccount.currency())
                                          .isExists(fromAccount.isExists())
                                          .value(fromAccount.value() - transferDto.value())
                                          .build();

      var updatedAccountTo = AccountsDto.builder()
                                        .currency(toAccount.currency())
                                        .isExists(toAccount.isExists())
                                        .value(toAccount.value() + transferDto.value())
                                        .build();
      accountsClient.updateAccount(List.of(updatedAccountFrom, updatedAccountTo)).subscribe();

    } else {
      log.warn("На счете недостаточно средств для перевода");
    }
  }
}