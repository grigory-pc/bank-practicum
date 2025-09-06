package ru.practicum.bank.front.ui.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.bank.front.ui.clients.exchange.generator.ExchangeGeneratorClient;
import ru.practicum.bank.front.ui.dto.Rate;

/**
 * Контроллер для запросов курса валюты.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class RateController {
private final ExchangeGeneratorClient exchangeGeneratorClient;
  /**
   * Обрабатывает запросы на получение курсов валют.
   */
    @GetMapping("/api/rates")
    public List<Rate> getRates() {
      log.info("получен запрос на получение курса валют");

      return exchangeGeneratorClient.getRates();
    }
}