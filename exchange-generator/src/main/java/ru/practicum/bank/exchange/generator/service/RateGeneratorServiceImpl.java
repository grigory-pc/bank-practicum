package ru.practicum.bank.exchange.generator.service;

import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.exchange.generator.dto.Rate;
import ru.practicum.bank.exchange.generator.enums.CurrencyExchange;
import ru.practicum.bank.exchange.generator.exceptions.CurrencyException;

@Slf4j
@Service
public class RateGeneratorServiceImpl implements RateGeneratorService {
  private static final double USD_RUB_MIN = 78.1;
  private static final double USD_RUB_MAX = 82.5;
  private static final double RUB_USD_MIN = 0.01185;
  private static final double RUB_USD_MAX = 0.01205;
  private static final double CNY_RUB_MIN = 10.9;
  private static final double CNY_RUB_MAX = 12.1;
  private static final double RUB_CNY_MIN = 0.08442;
  private static final double RUB_CNY_MAX = 0.08642;
  public static final String RUB_USD = "Курс рубля к доллару";
  public static final String USD_RUB = "Курс доллара к рублю";
  public static final String RUB_CNY = "Курс рубля к юаню";
  public static final String CNY_RUB = "Курс юаня к рублю";
  public static final double STEP_FOR_RANDOM_TO_RUB = 0.1;
  public static final double STEP_FOR_RANDOM_FROM_RUB = 0.0001;
  public static final double ROUND_DOUBLE_TO_RUB = 100.0;
  public static final double ROUND_DOUBLE_FROM_RUB = 1000000.0;
  private final Random random = new Random();

  @Override
  public List<Rate> getAllCurrency() {
    return List.of(generateCurrencyUsdRub(), generateCurrencyCnyRub());
  }

  @Override
  public Rate getCurrency(String currencyExchangeRequest) {
    CurrencyExchange currencyExchange = CurrencyExchange.getValueOf(currencyExchangeRequest);

    switch (currencyExchange) {
      case USD_RUB -> {
        return generateCurrencyUsdRub();
      }
      case RUB_USD -> {
        return generateCurrencyRubUsd();
      }
      case CNY_RUB -> {
        return generateCurrencyCnyRub();
      }
      case RUB_CNY -> {
        return generateCurrencyRubCny();
      }
      default -> throw new CurrencyException("некорректная валютная пара");
    }
  }


  private Rate generateCurrencyRubUsd() {
    var randomCurrencyUsd = random.nextDouble(RUB_USD_MAX - RUB_USD_MIN + STEP_FOR_RANDOM_FROM_RUB)
                            + RUB_USD_MIN;
    var roundedCurrencyUsd = Math.round(randomCurrencyUsd * ROUND_DOUBLE_FROM_RUB)
                             / ROUND_DOUBLE_FROM_RUB;

    return new Rate(CurrencyExchange.RUB_USD.name(), RUB_USD, roundedCurrencyUsd);
  }

  private Rate generateCurrencyRubCny() {
    var randomCurrencyCny = random.nextDouble(RUB_CNY_MAX - RUB_CNY_MIN + STEP_FOR_RANDOM_FROM_RUB)
                            + RUB_CNY_MIN;
    var roundedCurrencyCny = Math.round(randomCurrencyCny * ROUND_DOUBLE_FROM_RUB)
                             / ROUND_DOUBLE_FROM_RUB;

    return new Rate(CurrencyExchange.RUB_CNY.name(), RUB_CNY, roundedCurrencyCny);
  }

  private Rate generateCurrencyUsdRub() {
    var randomCurrencyUsd = random.nextDouble(USD_RUB_MAX - USD_RUB_MIN + STEP_FOR_RANDOM_TO_RUB)
                            + USD_RUB_MIN;
    var roundedCurrencyUsd = Math.round(randomCurrencyUsd * ROUND_DOUBLE_TO_RUB)
                             / ROUND_DOUBLE_TO_RUB;

    return new Rate(CurrencyExchange.USD_RUB.name(), USD_RUB, roundedCurrencyUsd);
  }

  private Rate generateCurrencyCnyRub() {
    var randomCurrencyCny = random.nextDouble(CNY_RUB_MAX - CNY_RUB_MIN + STEP_FOR_RANDOM_TO_RUB)
                            + CNY_RUB_MIN;
    var roundedCurrencyCny = Math.round(randomCurrencyCny * ROUND_DOUBLE_TO_RUB)
                             / ROUND_DOUBLE_TO_RUB;

    return new Rate(CurrencyExchange.CNY_RUB.name(), CNY_RUB, roundedCurrencyCny);
  }
}