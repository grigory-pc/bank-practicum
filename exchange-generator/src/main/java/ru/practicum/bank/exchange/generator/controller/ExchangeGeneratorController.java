package ru.practicum.bank.exchange.generator.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.bank.exchange.generator.dto.Rate;
import ru.practicum.bank.exchange.generator.service.RateGeneratorService;

/**
 * Контроллер для получения курсов валют.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ExchangeGeneratorController {
  private final RateGeneratorService rateGeneratorService;

  @GetMapping("/rates")
  public List<Rate> redirectToGetRates() {
    log.info("получен запрос на получение курса валют");

    return rateGeneratorService.getAllCurrency();
  }
}
