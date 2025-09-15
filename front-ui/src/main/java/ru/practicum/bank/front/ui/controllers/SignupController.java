package ru.practicum.bank.front.ui.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.bank.front.ui.clients.accounts.AccountsClient;
import ru.practicum.bank.front.ui.dto.UserDto;

/**
 * Контроллер обрабатывает запросы на регистрацию аккаунта в системе.
 */
@Slf4j
@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {

  public static final String SIGNUP_TEMPLATE = "signup";

  private final AccountsClient accountsClient;

  /**
   * Обрабатывает GET-запросы на открытие страницы регистрации аккаунта в системе.
   *
   * @return страница регистрации аккаунта в системе.
   */
  @GetMapping()
  public String getSignup() {
    log.info("Получен запрос на регистрацию аккаунта");

    return SIGNUP_TEMPLATE;
  }

  /**
   * Обрабатывает POST-запросы на добавление нового аккаунта.
   *
   * @param login           - логин пользователя.
   * @param password        - пароль пользователя.
   * @param confirmPassword - пароль пользователя второй раз.
   * @param name            - фамилия и имя пользователя.
   * @param birthdate       - дата рождения пользователя.
   * @return главная страница или станица регистрации в случае ошибки.
   */
  @PostMapping
  public String registerNewAccount(@RequestParam(value = "login") @NotBlank String login,
                                   @RequestParam(value = "password") @NotBlank String password,
                                   @RequestParam(value = "confirm_password")
                                   @NotBlank String confirmPassword,
                                   @RequestParam(value = "name") @NotBlank String name,
                                   @RequestParam(value = "birthdate")
                                   @NotNull LocalDate birthdate) {

    try {
      var newAccount = UserDto.builder()
                              .login(login)
                              .password(password)
                              .confirm_password(confirmPassword)
                              .name(name)
                              .birthdate(birthdate)
                              .build();

      accountsClient.requestCreateUser(newAccount).block();

      return "login";
    } catch (Exception e) {
      return SIGNUP_TEMPLATE;
    }
  }
}