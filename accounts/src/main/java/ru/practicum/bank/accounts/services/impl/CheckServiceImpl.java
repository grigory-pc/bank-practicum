package ru.practicum.bank.accounts.services.impl;

import java.time.LocalDate;
import java.time.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.bank.accounts.entity.Currency;
import ru.practicum.bank.accounts.exceptions.CurrencyException;
import ru.practicum.bank.accounts.repository.CurrencyRepository;
import ru.practicum.bank.accounts.services.CheckService;

@Service
@RequiredArgsConstructor
public class CheckServiceImpl implements CheckService {
  private final CurrencyRepository currencyRepository;

  @Override
  public Boolean checkPassword(String password, String confirmPassword) {
    return password.equals(confirmPassword);
  }

  @Override
  public Boolean checkBirthdate(LocalDate birthDate) {
    var today = LocalDate.now();
    var period = Period.between(birthDate, today);

    return period.getYears() >= 18;
  }

  @Override
  public Currency checkAndGetCurrency(String currency) throws CurrencyException {
    var currencyFromDb = currencyRepository.findByName(currency);

    return currencyFromDb.orElseThrow(() -> new CurrencyException("Неподдерживаемый тип валюты"));
  }
}