package ru.practicum.bank.front.ui.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.practicum.bank.front.ui.clients.exchange.ExchangeClient;
import ru.practicum.bank.front.ui.dto.Rate;

/**
 * Контроллер для запросов курса валюты.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class RateController {
private final ExchangeClient exchangeClient;
  /**
   * Обрабатывает запросы на получение курсов валют.
   */
    @GetMapping("/api/rates")
    public Mono<List<Rate>> getRates() {
      log.info("получен запрос на получение курса валют");

      return exchangeClient.getRates();
    }
}