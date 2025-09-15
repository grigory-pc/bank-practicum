package ru.practicum.bank.accounts.services.impl;

import java.time.LocalDate;
import java.time.Period;
import org.springframework.stereotype.Service;
import ru.practicum.bank.accounts.services.CheckService;

@Service
public class CheckServiceImpl implements CheckService {
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
}
