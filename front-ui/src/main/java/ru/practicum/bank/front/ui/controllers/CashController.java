package ru.practicum.bank.front.ui.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.bank.front.ui.clients.cash.CashClient;
import ru.practicum.bank.front.ui.dto.CashDto;
import ru.practicum.bank.front.ui.enums.Action;

/**
 * Контроллер для обработки запросов на операции с балансом на счете.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class CashController {
  public static final String REDIRECT_MAIN = "redirect:/main";
  private final CashClient cashClient;

  /**
   * Метод для обработки запросов на снятие или зачисление денег на счет.
   *
   * @param login    - логин пользователя.
   * @param currency - строка с валютой.
   * @param value    - сумма внесения/снятия.
   * @param action   - действие (enum PUT иди GET).
   * @return редирект на "/main".
   */
  @PostMapping("/user/{login}/сash")
  public String getCash(@PathVariable(value = "login") @NotBlank String login,
                        @RequestParam(value = "currency") @NotBlank String currency,
                        @RequestParam(value = "value") @NotBlank Integer value,
                        @RequestParam(value = "action") @NotBlank Action action) {
    log.info("получен запрос на операцию с наличными: {} для пользователя: {}", action.name(),
             login);

    var cashDto = CashDto.builder()
                         .login(login)
                         .currency(currency)
                         .value(value)
                         .action(action)
                         .build();

    cashClient.requestCash(cashDto).block();

    return REDIRECT_MAIN;
  }
}