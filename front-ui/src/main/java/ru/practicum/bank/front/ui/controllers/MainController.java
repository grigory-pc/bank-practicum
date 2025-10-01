package ru.practicum.bank.front.ui.controllers;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.RedirectView;
import ru.practicum.bank.front.ui.clients.accounts.AccountsClient;

/**
 * Контроллер обрабатывает запросы на странице банковского приложения.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
  public static final String MAIN_TEMPLATE = "main";
  private final AccountsClient accountsClient;

  /**
   * Перенаправление запросов с "/" на "/main".
   *
   * @return redirect /main.
   */
  @GetMapping("/")
  public RedirectView redirectToMain() {
    return new RedirectView("/main");
  }

  /**
   * Обрабатывает GET-запросы на открытие главной страницы сервиса.
   *
   * @return главная страница.
   */
  @GetMapping("/main")
  public String getMain(Principal principal, Model model) {
    log.info("Получен запрос на открытие главной страницы для аккаунта: ");

    var userFull = accountsClient.requestGetUser(principal.getName()).block();

    model.addAttribute("login", userFull.login());
    model.addAttribute("name", userFull.name());
    model.addAttribute("birthdate", userFull.birthdate());
    model.addAttribute("accounts", userFull.accounts());
    model.addAttribute("currency", userFull.currency());
    model.addAttribute("users", userFull.users());
    model.addAttribute("passwordErrors", userFull.passwordErrors());
    model.addAttribute("userAccountsErrors", userFull.userAccountsErrors());
    model.addAttribute("cashErrors", userFull.cashErrors());
    model.addAttribute("transferErrors", userFull.transferErrors());
    model.addAttribute("transferErrors", userFull.transferOtherErrors());

    return MAIN_TEMPLATE;
  }
}