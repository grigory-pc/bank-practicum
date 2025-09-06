package ru.practicum.bank.front.ui.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Контроллер обрабатывает запросы на странице банковского приложения.
 */
@Slf4j
@Controller
public class MainController {
  public static final String MAIN_TEMPLATE = "main";


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
  public String getMain() {
    log.info("Получен запрос на открытие главной страницы для аккаунта: ");

    return MAIN_TEMPLATE;
  }
}