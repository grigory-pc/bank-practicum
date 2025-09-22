package ru.practicum.bank.accounts.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.accounts.dto.AccountRequestDto;
import ru.practicum.bank.accounts.dto.AccountsDto;
import ru.practicum.bank.accounts.mappers.AccountMapper;
import ru.practicum.bank.accounts.repository.AccountRepository;
import ru.practicum.bank.accounts.repository.CurrencyRepository;
import ru.practicum.bank.accounts.repository.UserRepository;
import ru.practicum.bank.accounts.services.AccountService;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
  private final UserRepository userRepository;
  private final CurrencyRepository currencyRepository;
  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;

  @Override
  public AccountsDto getAccount(AccountRequestDto accountRequest) {
    var user = userRepository.findByLogin(accountRequest.login());
    var currency = currencyRepository.findByName(accountRequest.currency());

    return accountMapper.toDto(accountRepository.findByCurrencyAndUser(currency.get(), user).get());
  }

  @Override
  public void updateAccount(AccountsDto accountsDto) {
    accountRepository.save(accountMapper.toAccount(accountsDto));

    log.info("Обновлен объект аккаунта");
  }
}
