package ru.practicum.bank.front.ui.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер обрабатывает запросы на регистрацию аккаунта в системе.
 */
@Slf4j
@Controller
@RequestMapping("/signup")
public class SignupController {
  /**
   * Обрабатывает GET-запросы на открытие страницы регистрации аккаунта в системе.
   *
   * @param model - модель данных.
   * @return страница регистрации аккаунта в системе.
   */
  @GetMapping()
  public String getSignup(Model model) {
    log.info("Получен запрос на регистрацию аккаунта");

    return "signup";
  }

  /**
   * Обрабатывает POST-запросы на добавление нового аккаунта.
   *
   * @param login           - логин пользователя.
   * @param password        - пароль пользователя.
   * @param confirmPassword - пароль пользователя второй раз.
   * @param name            - фамилия и имя пользователя.
   * @param birthdate       - дата рождения пользователя.
   * @param model           - модель данных.
   * @return главная страница или станица регистрации в случае ошибки.
   */
  @PostMapping
  public String registerNewAccount(@RequestParam(value = "login") @NotBlank String login,
                                   @RequestParam(value = "password") @NotBlank String password,
                                   @RequestParam(value = "confirm_password")
                                   @NotBlank String confirmPassword,
                                   @RequestParam(value = "name") @NotBlank String name,
                                   @RequestParam(value = "birthdate") @NotBlank String birthdate,
                                   Model model) {

    try {
      return "main";
    } catch (Exception e) {
      return "signup";
    }
  }
}