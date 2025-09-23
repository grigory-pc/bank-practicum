package ru.practicum.bank.transfer.services;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.transfer.clients.accounts.AccountsClient;
import ru.practicum.bank.transfer.dto.AccountsDto;
import ru.practicum.bank.transfer.dto.TransferDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
  private final AccountsClient accountsClient;

  @Transactional
  @Override
  public void transferToSelfAccount(TransferDto transferDto) {
    var fromAccount = accountsClient.requestGetAccount(transferDto.login(),
                                                       transferDto.fromCurrency()).block();
    var toAccount = accountsClient.requestGetAccount(transferDto.login(), transferDto.toCurrency())
                                  .block();

    if (transferDto.fromCurrency().equals(transferDto.toCurrency())) {
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

  @Override
  public void transferToOtherAccount(TransferDto transferDto) {

  }
}