package ru.practicum.bank.exchange.generator.controller;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.bank.exchange.generator.dto.Rate;
import ru.practicum.bank.exchange.generator.service.RateGeneratorService;

/**
 * Контроллер для получения курсов валют.
 */
@Slf4j
@RestController
@RequestMapping("exchange-generator")
@RequiredArgsConstructor
public class ExchangeGeneratorController {
  private final RateGeneratorService rateGeneratorService;

  @GetMapping
  public List<Rate> redirectToGetRates() {
    log.info("получен запрос на получение списка курсов валют");

    return rateGeneratorService.getAllCurrency();
  }

  @GetMapping("/{currencyExchange}")
  public Rate getCurrencyRate(@PathVariable @NotBlank String currencyExchange) {
    log.info("получен запрос на получение всех курсов валют");

    return rateGeneratorService.getCurrency(currencyExchange);
  }
}
