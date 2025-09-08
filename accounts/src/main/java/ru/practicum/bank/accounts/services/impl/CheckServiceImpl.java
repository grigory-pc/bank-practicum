package ru.practicum.bank.accounts.services.impl;

import org.springframework.stereotype.Service;
import ru.practicum.bank.accounts.services.CheckService;

@Service
public class CheckServiceImpl implements CheckService {
  @Override
  public Boolean checkPassword(String password, String confirmPassword) {
    return password.equals(confirmPassword);
  }
}
