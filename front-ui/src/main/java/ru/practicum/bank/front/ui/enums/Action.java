package ru.practicum.bank.front.ui.enums;

import ch.qos.logback.core.joran.spi.ActionException;

public enum Action {
  GET,
  PUT;

  public static Action getValueOf(String action) throws ActionException {
    for (Action type : Action.values()) {
      if (type.name().equalsIgnoreCase(action)) {
        return type;
      }
    }
    throw new ActionException("Некорректный тип действия с балансом");
  }
}