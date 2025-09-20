package ru.practicum.bank.exchange.generator.service;

import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.exchange.generator.dto.Rate;
import ru.practicum.bank.exchange.generator.enums.CurrencyExchange;

@Slf4j
@Service
public class RateGeneratorServiceImpl implements RateGeneratorService {
  private static final double RUB_USD_MIN = 78.1;
  private static final double RUB_USD_MAX = 82.5;
  private static final double RUB_CNY_MIN = 10.9;
  private static final double RUB_CNY_MAX = 12.1;
  public static final String RUB_USD = "Курс рубля к доллару";
  public static final String RUB_CNY = "Курс рубля к юаню";
  public static final double STEP_FOR_RANDOM = 0.1;
  public static final double ROUND_DOUBLE = 100.0;
  private final Random random = new Random();

  @Override
  public List<Rate> getAllCurrency() {
    return List.of(generateCurrencyRubUsd(), generateCurrencyRubCny());
  }

  private Rate generateCurrencyRubUsd() {
    var randomCurrencyUsd = random.nextDouble(RUB_USD_MAX - RUB_USD_MIN + STEP_FOR_RANDOM)
                            + RUB_USD_MIN;
    var roundedCurrencyUsd = Math.round(randomCurrencyUsd * ROUND_DOUBLE) / ROUND_DOUBLE;

    return new Rate(CurrencyExchange.RUB_USD.name(), RUB_USD, roundedCurrencyUsd);
  }

  private Rate generateCurrencyRubCny() {
    var randomCurrencyCny = random.nextDouble(RUB_CNY_MAX - RUB_CNY_MIN + STEP_FOR_RANDOM)
                            + RUB_CNY_MIN;
    var roundedCurrencyCny = Math.round(randomCurrencyCny * ROUND_DOUBLE) / ROUND_DOUBLE;

    return new Rate(CurrencyExchange.RUB_CNY.name(), RUB_CNY, roundedCurrencyCny);
  }
}