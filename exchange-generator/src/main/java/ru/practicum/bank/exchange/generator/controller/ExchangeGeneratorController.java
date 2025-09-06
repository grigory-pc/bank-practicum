package ru.practicum.bank.exchange.generator.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.practicum.bank.exchange.generator.dto.CurrencyDto;
import ru.practicum.bank.exchange.generator.service.RateGeneratorService;

/**
 * Контроллер для получения курсов валют.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ExchangeGeneratorController {
  private final RateGeneratorService rateGeneratorService;

  @GetMapping("/api/rates")
  public List<CurrencyDto> redirectToGetRates() {
    log.info("получен запрос на получение курса валют");

    return rateGeneratorService.getAllCurrency();
  }
}
