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
    var findedUser = userRepository.findByLogin(login);
    var findedCurrency = currencyRepository.findByName(currency);

    return accountMapper.toDto(
        accountRepository.findByCurrencyAndUser(findedCurrency.get(), findedUser).get());
  }

  @Override
  @Transactional
  public void updateAccount(AccountsDto accountsDto) {
    var account = accountMapper.toAccount(accountsDto);

    currencyRepository.save(account.getCurrency());
    accountRepository.save(account);

    log.info("Обновлен объект аккаунта");
  }
}
