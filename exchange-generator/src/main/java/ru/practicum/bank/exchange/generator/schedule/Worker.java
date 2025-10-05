package ru.practicum.bank.exchange.generator.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.practicum.bank.exchange.generator.clients.exchange.ExchangeClient;
import ru.practicum.bank.exchange.generator.service.RateGeneratorService;

@Component
@RequiredArgsConstructor
public class Worker {
  private final RateGeneratorService rateGeneratorService;
  private final ExchangeClient exchangeClient;

  @Scheduled(fixedRate = 300_000)
  public void run() {
    var rates = rateGeneratorService.getAllCurrency();
    exchangeClient.postRates(rates).subscribe();
  }
}
