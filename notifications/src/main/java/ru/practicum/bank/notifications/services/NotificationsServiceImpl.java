package ru.practicum.bank.notifications.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.notifications.dto.AccountsDto;
import ru.practicum.bank.notifications.dto.CashDto;
import ru.practicum.bank.notifications.dto.TransferDto;
import ru.practicum.bank.notifications.dto.UserNotifyDto;
import ru.practicum.bank.notifications.enums.Action;

@Slf4j
@Service
public class NotificationsServiceImpl implements NotificationsService {
  @Override
  public void transferNotification(TransferDto transferDto) {
    System.out.printf("C Вашего счета отправлена сумма %d %s%n", transferDto.value(),
                      transferDto.fromCurrency());

    log.info("Отправлено уведомление о списании средств: {} {}", transferDto.value(),
             transferDto.fromCurrency());

    System.out.printf("Ваш счет пополнен на сумму %d %s%n", transferDto.value(),
                      transferDto.fromCurrency());

    log.info("Отправлено уведомление о пополнении средств: {} {}", transferDto.value(),
             transferDto.fromCurrency());
  }

  @Override
  public void cashNotification(CashDto cashDto) {
    var action = Action.getValueOf(cashDto.action());

    switch (action) {
      case GET -> {
        System.out.printf("C Вашего счета списана сумма %d %s%n", cashDto.value(),
                          cashDto.currency());

        log.info("Отправлено уведомление о списании средств: {} {}", cashDto.value(),
                 cashDto.currency());
      }
      case PUT -> {

        System.out.printf("Ваш счет пополнен на сумму %d %s%n", cashDto.value(),
                          cashDto.currency());

        log.info("Отправлено уведомление о пополнении средств: {} {}", cashDto.value(),
                 cashDto.currency());
      }
    }
  }

  @Override
  public void userNotification(UserNotifyDto userNotifyDto) {
    for (AccountsDto accountsDto : userNotifyDto.getAccounts()) {
      System.out.printf("Для пользователя {} добавлен счет: {}", userNotifyDto.getName(),
                        accountsDto.currency().name());

      log.info("Отправлено уведомление пользователю {} о создании счета: {}",
               userNotifyDto.getName(), accountsDto.currency().title());
    }
  }
}