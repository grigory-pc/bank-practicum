package ru.practicum.bank.exchange.generator.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.exchange.generator.dto.Rate;
import ru.practicum.bank.exchange.generator.enums.Currency;

@Slf4j
@Service
public class RateGeneratorServiceImpl implements RateGeneratorService{
  @Override
  public List<Rate> getAllCurrency() {
    return List.of(new Rate(Currency.RUB.name(), "Рубли", 80));
  }
}
