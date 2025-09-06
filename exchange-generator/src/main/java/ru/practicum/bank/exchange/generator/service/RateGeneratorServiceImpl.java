package ru.practicum.bank.exchange.generator.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.exchange.generator.dto.CurrencyDto;
import ru.practicum.bank.exchange.generator.enums.Currency;

@Slf4j
@Service
public class RateGeneratorServiceImpl implements RateGeneratorService{
  @Override
  public List<CurrencyDto> getAllCurrency() {
    return List.of(new CurrencyDto(Currency.RUB.name(), "Рубли", 80));
  }
}
