package ru.practicum.bank.accounts.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.bank.accounts.dto.UserAuthDto;
import ru.practicum.bank.accounts.dto.UserDto;
import ru.practicum.bank.accounts.dto.UserFullDto;
import ru.practicum.bank.accounts.exceptions.PasswordException;
import ru.practicum.bank.accounts.services.UserService;

/**
 * Контроллер для обработки запросов на манипуляции с пользователями.
 */
@Slf4j
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  /**
   * Создание пользователя.
   *
   * @param userDto - объект нового пользователя.
   */
  @PostMapping("/create")
  public void addUser(@Valid @RequestBody UserDto userDto) throws PasswordException {
    log.info("Получен запрос на добавление нового пользователя");

    userService.addUser(userDto);
  }

  /**
   * Изменение пароля пользователя.
   *
   * @param userDto - объект пользователя.
   */
  @PatchMapping("/edit/password")
  public void updateUserPassword(@RequestBody UserDto userDto) throws PasswordException {
    log.info("Получен запрос на изменение пароля  нового пользователя");

    userService.updateUserPassword(userDto);
  }

  /**
   * Обновление данных пользователя.
   *
   * @param userDto - объект нового пользователя.
   */
  @PatchMapping("/edit/data")
  public void updateUserData(@RequestBody UserDto userDto) {
    log.info("Получен запрос на обновление данных пользователя");

    userService.updateUserData(userDto);
  }

  /**
   * Получение полной информации о пользователе.
   *
   * @param login - логин пользователя.
   */
  @GetMapping("/user/{login}")
  public UserFullDto getUserFullData(@PathVariable @NotBlank String login) {
    log.info("Получен запрос на получение полной информации о пользователе: {}", login);

    return userService.getUserFullByLogin(login);
  }

  /**
   * Получение основной информации о пользователе.
   *
   * @param login - логин пользователя.
   */
  @GetMapping("/auth/{login}")
  public UserAuthDto getUser(@PathVariable @NotBlank String login) {
    log.info("Получен запрос на получение основной информации о пользователе: {}", login);

    return userService.getUserByLogin(login);
  }
}
