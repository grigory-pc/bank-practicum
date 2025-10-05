package ru.practicum.bank.exchange.generator.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.reactive.DeferringLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.exchange.generator.clients.exchange.ExchangeClient;
import ru.practicum.bank.exchange.generator.clients.exchange.ExchangeClientImpl;
import ru.practicum.bank.exchange.generator.exceptions.NegativeDurationException;

@Configuration
public class ExchangeClientBinding {
  public static final String EXCHANGE_WEB_CLIENT = "ExchangeWebClient";

  @Bean(EXCHANGE_WEB_CLIENT)
  public WebClient getExchangeGeneratorWebClient(ExchangeClientProps props,
                                                 DeferringLoadBalancerExchangeFilterFunction<LoadBalancedExchangeFilterFunction> exchangeFilterFunction)
      throws NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl(), exchangeFilterFunction);
  }

  @Bean
  public ExchangeClient exchangeGeneratorClient(
      @Qualifier(EXCHANGE_WEB_CLIENT) WebClient webClient) {
    return new ExchangeClientImpl(webClient);
  }
}