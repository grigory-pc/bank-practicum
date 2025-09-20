package ru.practicum.bank.accounts.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.accounts.dto.CashChangeRequestDto;
import ru.practicum.bank.accounts.entity.Account;
import ru.practicum.bank.accounts.exceptions.CurrencyException;
import ru.practicum.bank.accounts.repository.AccountRepository;
import ru.practicum.bank.accounts.repository.UserRepository;
import ru.practicum.bank.accounts.services.CashService;
import ru.practicum.bank.accounts.services.CheckService;

@Slf4j
@Service
@RequiredArgsConstructor
public class CashServiceImpl implements CashService {
  private final AccountRepository accountRepository;
  private final UserRepository userRepository;
  private final CheckService checkService;

  @Override
  public void getCashFromAccount(CashChangeRequestDto cashRequestDto) throws CurrencyException {
    var account = getAccount(cashRequestDto);

    if (Boolean.TRUE.equals(account.getIsExists())) {
      log.info("Счёт пользователя: {} для валюты: {} найден", cashRequestDto.currency(),
               cashRequestDto.login());

      if (account.getValue() > cashRequestDto.value()) {
        account.setValue(account.getValue() - cashRequestDto.value());
        accountRepository.save(account);
      } else {
        log.warn("На счёте недостаточно средств для снятия. На текущем счёте: {}",
                 account.getValue());
      }
    } else {
      log.warn("Аккаунт пользователя не активен");
    }
  }

  @Override
  public void putCashToAccount(CashChangeRequestDto cashRequestDto) {

    var account = getAccount(cashRequestDto);

    if (Boolean.TRUE.equals(account.getIsExists())) {
      log.info("Счёт пользователя: {} для валюты: {} найден", cashRequestDto.currency(),
               cashRequestDto.login());

      account.setValue(account.getValue() + cashRequestDto.value());
      accountRepository.save(account);

    } else {
      log.info("Аккаунт пользователя не активен. Производится активация и пополнение счета");

      account.setValue(cashRequestDto.value());
      account.setIsExists(true);

      accountRepository.save(account);
    }
  }

  private Account getAccount(CashChangeRequestDto cashRequestDto) {
    var currency = checkService.checkAndGetCurrency(cashRequestDto.currency());

    var user = userRepository.findByLogin(cashRequestDto.login());

    log.info("Отправлен запрос на поиск валютного счёта: {} для пользователя: {}",
             currency.getTitle(), user.getLogin());

    return accountRepository.findByCurrencyAndUser(currency, user).get();
  }
}