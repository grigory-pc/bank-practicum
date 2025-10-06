package ru.practicum.bank.accounts.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
  public AccountsDto getAccount(String login, String currency) {
    var foundUser = userRepository.findByLogin(login);
    var foundCurrency = currencyRepository.findByName(currency);

    return accountMapper.toDto(
        accountRepository.findByCurrencyAndUser(foundCurrency.get(), foundUser).get());
  }

  @Override
  @Transactional
  public void updateAccount(AccountsDto accountsDto) {
    var account = accountMapper.toAccount(accountsDto);

    var user = userRepository.findById(accountsDto.userId());
    var existingCurrency = currencyRepository.findByName(account.getCurrency().getName())
                                             .orElseThrow(
                                                 () -> new RuntimeException("Валюта не найдена"));

    account.setUser(user.get());
    account.setCurrency(existingCurrency);
    accountRepository.save(account);

    log.info("Обновлен объект аккаунта");
  }
}
