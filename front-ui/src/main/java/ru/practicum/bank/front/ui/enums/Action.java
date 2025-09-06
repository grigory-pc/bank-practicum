package ru.practicum.bank.front.ui.enums;


import ru.practicum.bank.front.ui.exceptions.ActionException;

/**
 * Возможные действия с денежными средствами.
 */
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