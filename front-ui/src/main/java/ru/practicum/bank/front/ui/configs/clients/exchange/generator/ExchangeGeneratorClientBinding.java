package ru.practicum.bank.front.ui.configs.clients.exchange.generator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.front.ui.clients.exchange.generator.ExchangeGeneratorClient;
import ru.practicum.bank.front.ui.clients.exchange.generator.ExchangeGeneratorClientImpl;
import ru.practicum.bank.front.ui.configs.clients.DefaultWebClientFactory;
import ru.practicum.bank.front.ui.exceptions.NegativeDurationException;

@Configuration
public class ExchangeGeneratorClientBinding {
  public static final String EXCHANGE_GENERATOR_WEB_CLIENT = "ExchangeGeneratorWebClient";

  @Bean(EXCHANGE_GENERATOR_WEB_CLIENT)
  public WebClient getExchangeGeneratorWebClient(ExchangeGeneratorClientProps props) throws NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl());
  }

  @Bean
  public ExchangeGeneratorClient exchangeGeneratorClient(
      @Qualifier(EXCHANGE_GENERATOR_WEB_CLIENT) WebClient webClient) {
    return new ExchangeGeneratorClientImpl(webClient);
  }
}