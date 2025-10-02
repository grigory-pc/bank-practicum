package ru.practicum.bank.front.ui.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import ru.practicum.bank.front.ui.clients.transfer.TransferClient;
import ru.practicum.bank.front.ui.dto.TransferDto;

/**
 * Контроллер для перевода денег между своими счетами и перевода денег на счет другого
 * пользователя.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class TransferController {
  public static final String REDIRECT_MAIN = "redirect:/main";
  private final TransferClient transferClient;

  /**
   * Метод для обработки запросов на перевод средств.
   *
   * @param login        - логин пользователя.
   * @param fromCurrency - строка с валютой счета, с которого переводятся деньги.
   * @param toCurrency   - строка с валютой счета, на который переводятся деньги.
   * @param value        - сумма внесения/снятия.
   * @param toLogin      - логин пользователя, которому переводятся деньги.
   * @return редирект на "/main".
   */
  @PostMapping("/user/{login}/transfer")
  public Mono<String> getCash(@PathVariable(value = "login") String login,
                              @RequestParam(value = "from_currency") @NotBlank String fromCurrency,
                              @RequestParam(value = "to_currency") @NotBlank String toCurrency,
                              @RequestParam(value = "value") @NotNull Integer value,
                              @RequestParam(value = "to_login") @NotBlank String toLogin) {
    log.info("получен запрос на перевод средств со счета: {} на счет: {}", fromCurrency,
             toCurrency);

    var transferDto = TransferDto.builder()
                                 .login(login)
                                 .fromCurrency(fromCurrency)
                                 .toCurrency(toCurrency)
                                 .value(value)
                                 .toLogin(toLogin)
                                 .build();

    return transferClient.requestTransfer(transferDto).then(Mono.just(REDIRECT_MAIN));
  }
}