package ru.practicum.bank.exchange.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.reactive.DeferringLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.exchange.clients.ExchangeGeneratorClient;
import ru.practicum.bank.exchange.clients.ExchangeGeneratorClientImpl;
import ru.practicum.bank.exchange.exceptions.NegativeDurationException;

@Configuration
public class ExchangeGeneratorClientBinding {
  public static final String EXCHANGE_GENERATOR_WEB_CLIENT = "ExchangeGeneratorWebClient";

  @Bean(EXCHANGE_GENERATOR_WEB_CLIENT)
  public WebClient getExchangeGeneratorWebClient(ExchangeGeneratorClientProps props,
                                                 DeferringLoadBalancerExchangeFilterFunction<LoadBalancedExchangeFilterFunction> exchangeFilterFunction)
      throws NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl(), exchangeFilterFunction);
  }

  @Bean
  public ExchangeGeneratorClient exchangeGeneratorClient(
      @Qualifier(EXCHANGE_GENERATOR_WEB_CLIENT) WebClient webClient) {
    return new ExchangeGeneratorClientImpl(webClient);
  }
}