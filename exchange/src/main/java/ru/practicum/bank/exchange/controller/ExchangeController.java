package ru.practicum.bank.exchange.controller;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.bank.exchange.clients.ExchangeGeneratorClient;
import ru.practicum.bank.exchange.dto.Rate;

/**
 * Контроллер для получения курсов валют.
 */
@Slf4j
@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class ExchangeController {
  private final ExchangeGeneratorClient exchangeGeneratorClient;

  @GetMapping
  public List<Rate> getRates() {
    log.info("получен запрос на получение всех курсов валют");

    return exchangeGeneratorClient.getRates();
  }
  @GetMapping("/{currencyExchange}")
  public Rate getCurrencyRate(@PathVariable @NotBlank String currencyExchange) {
    log.info("получен запрос на получение всех курсов валют");

    return exchangeGeneratorClient.getCurrencyRate(currencyExchange).block();
  }
}