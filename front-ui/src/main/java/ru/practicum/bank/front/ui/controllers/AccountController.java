package ru.practicum.bank.front.ui.controllers;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import ru.practicum.bank.front.ui.clients.accounts.AccountsClient;
import ru.practicum.bank.front.ui.dto.UserDto;

/**
 * Контроллер для обработки запросов на модификацию аккаунта.
 */
@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class AccountController {
  public static final String REDIRECT_MAIN = "redirect:/main";
  public static final String EDIT_USER_PASSWORD = "/edit/password";
  public static final String EDIT_USER_DATA = "/edit/data";
  private final AccountsClient accountsClient;

  /**
   * Обработка запросов на изменение пароля.
   *
   * @param login           - логин пользователя.
   * @param password        - пароль пользователя.
   * @param confirmPassword - пароль пользователя второй раз.
   * @return главная страница.
   */
  @PostMapping("/{login}/editPassword")
  public Mono<String> editAccountPassword(@PathVariable(value = "login") @NotBlank String login,
                                          @RequestParam(value = "password")
                                          @NotBlank String password,
                                          @RequestParam(value = "confirm_password")
                                          @NotBlank String confirmPassword) {
    log.info("получен запрос на изменение пароля");

    var account = UserDto.builder()
                         .login(login)
                         .password(password)
                         .confirm_password(confirmPassword)
                         .build();

    accountsClient.requestEditUser(EDIT_USER_PASSWORD, account).block();

    return accountsClient.requestEditUser(EDIT_USER_PASSWORD, account)
                         .then(Mono.just(REDIRECT_MAIN));
  }

  /**
   * Обработка изменение данных пользователя.
   *
   * @param login     - логин пользователя.
   * @param name      - фамилия и имя пользователя.
   * @param birthdate - дата рождения пользователя.
   * @return главная страница.
   */
  @PostMapping("/{login}/editUserAccounts")
  public Mono<String> editUserAccounts(@PathVariable(value = "login") @NotBlank String login,
                                       @RequestParam(value = "name") @NotBlank String name,
                                       @RequestParam(value = "birthdate")
                                       @NotBlank LocalDate birthdate) {
    log.info("получен запрос на изменение данных пользователя");

    var account = UserDto.builder()
                         .login(login)
                         .name(name)
                         .birthdate(birthdate)
                         .build();

    return accountsClient.requestEditUser(EDIT_USER_DATA, account).then(Mono.just(REDIRECT_MAIN));

  }
}