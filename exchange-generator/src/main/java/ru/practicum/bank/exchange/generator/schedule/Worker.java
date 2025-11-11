package ru.practicum.bank.exchange.generator.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.practicum.bank.exchange.generator.service.KafkaService;
import ru.practicum.bank.exchange.generator.service.RateGeneratorService;

@Component
@RequiredArgsConstructor
public class Worker {
  private final RateGeneratorService rateGeneratorService;
  private final KafkaService kafkaService;

  @Scheduled(fixedRate = 60_000)
  public void run() {
    var rates = rateGeneratorService.getAllCurrency();
    kafkaService.sendMessage(rates);
  }
}