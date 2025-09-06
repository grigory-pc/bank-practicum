package ru.practicum.bank.front.ui.controllers;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер для обработки запросов на модификацию аккаунта.
 */
@Slf4j
@Controller
@RequestMapping("/user/login")
@RequiredArgsConstructor
public class AccountController {
  public static final String REDIRECT_MAIN = "redirect:/main";

  /**
   * Обработка запросов на изменение пароля.
   *
   * @param login           - логин пользователя.
   * @param password        - пароль пользователя.
   * @param confirmPassword - пароль пользователя второй раз.
   * @return главная страница.
   */
  @PostMapping("/editPassword")
  public String editUserPassword(@RequestParam(value = "login") @NotBlank String login,
                                 @RequestParam(value = "password") @NotBlank String password,
                                 @RequestParam(value = "confirm_password")
                                 @NotBlank String confirmPassword) {
    log.info("получен запрос на изменение пароля");

    return REDIRECT_MAIN;
  }

  /**
   * Обработка изменение данных пользователя.
   *
   * @param login     - логин пользователя.
   * @param name      - фамилия и имя пользователя.
   * @param birthdate - дата рождения пользователя.
   * @return главная страница.
   */
  @PostMapping("/editUserAccounts")
  public String editUserAccounts(@RequestParam(value = "login") @NotBlank String login,
                                 @RequestParam(value = "name") @NotBlank String name,
                                 @RequestParam(value = "birthdate") @NotBlank String birthdate,
                                 @RequestParam(value = "account") @NotBlank List<String> account) {
    log.info("получен запрос на изменение данных пользователя");

    return REDIRECT_MAIN;
  }
}