package ru.practicum.bank.front.ui.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.bank.front.ui.enums.Action;

/**
 * Контроллер для обработки запросов на операции с деньгами на счете.
 */
@Slf4j
@Controller
public class CashController {
  public static final String REDIRECT_MAIN = "redirect:/main";

  /**
   * Метод для обработки запросов на снятие или зачисление денег на счет.
   *
   * @param login    - логин пользователя.
   * @param currency - строка с валютой.
   * @param value    - сумма внесения/снятия.
   * @param action   - действие (enum PUT иди GET).
   * @return редирект на "/main".
   */
  @PostMapping("/user/login/сash")
  public String getCash(@RequestParam(value = "login") @NotBlank String login,
                        @RequestParam(value = "currency") @NotBlank String currency,
                        @RequestParam(value = "value") @NotBlank Integer value,
                        @RequestParam(value = "action") @NotBlank Action action) {
    log.info("получен запрос на операцию с наличными: {} для пользователя: {}", action.name(),
             login);

    return REDIRECT_MAIN;
  }
}