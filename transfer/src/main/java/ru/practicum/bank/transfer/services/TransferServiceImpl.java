package ru.practicum.bank.transfer.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.transfer.clients.accounts.AccountsClient;
import ru.practicum.bank.transfer.dto.TransferDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
  private final AccountsClient accountsClient;

  @Override
  public void transferToSelfAccount(TransferDto transferDto) {
    

  }

  @Override
  public void transferToOtherAccount(TransferDto transferDto) {

  }
}