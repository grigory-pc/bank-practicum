package ru.practicum.bank.accounts.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.accounts.dto.CashChangeRequestDto;
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
    var currency = checkService.checkAndGetCurrency(cashRequestDto.currency());

    var user = userRepository.findByLogin(cashRequestDto.login());

    log.info("Отправлен запрос на поиск валютного счёта: {} для пользователя: {}",
             currency.getTitle(), user.getLogin());

    var account = accountRepository.findAllByUser(user).stream()
                                   .filter(findedAccount -> findedAccount.getCurrency()
                                                                         .equals(currency))
                                   .findFirst();

    if (account.isPresent()) {
      log.info("Счёт пользователя: {} для валюты: {} найден", user.getLogin(), currency.getTitle());

      var updatedAccount = account.get();

      if (updatedAccount.getValue() > cashRequestDto.value()) {
        updatedAccount.setValue(updatedAccount.getValue() - cashRequestDto.value());
        accountRepository.save(updatedAccount);
      } else {
        log.warn("На счёте недостаточно средств для снятия. На текущем счёте ");
      }
    }
  }

  @Override
  public void putCashToAccount(CashChangeRequestDto cashRequestDto) {

  }
}
