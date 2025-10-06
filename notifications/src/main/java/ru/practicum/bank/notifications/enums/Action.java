package ru.practicum.bank.notifications.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import ru.practicum.bank.notifications.exceptions.ActionException;

/**
 * Возможные действия с денежными средствами.
 */
@RequiredArgsConstructor
public enum Action {
  GET("GET"),
  PUT("PUT");

  @JsonValue
  public final String value;

  @JsonCreator
  public static Action getValueOf(String action) throws ActionException {
    for (Action type : Action.values()) {
      if (type.value.equalsIgnoreCase(action)) {
        return type;
      }
    }
    throw new ActionException("Некорректный тип действия с балансом");
  }
}