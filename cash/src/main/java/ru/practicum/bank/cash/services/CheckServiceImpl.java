package ru.practicum.bank.cash.services;

import org.springframework.stereotype.Service;
import ru.practicum.bank.cash.enums.Action;

@Service
public class CheckServiceImpl implements CheckService {
  @Override
  public Action checkAndGetAction(String action) {
    return Action.getValueOf(action);
  }
}