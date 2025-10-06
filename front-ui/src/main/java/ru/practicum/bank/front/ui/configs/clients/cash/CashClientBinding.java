package ru.practicum.bank.front.ui.configs.clients.cash;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.reactive.DeferringLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.front.ui.clients.cash.CashClient;
import ru.practicum.bank.front.ui.clients.cash.CashClientImpl;
import ru.practicum.bank.front.ui.configs.clients.DefaultWebClientFactory;
import ru.practicum.bank.front.ui.exceptions.NegativeDurationException;

@Configuration
public class CashClientBinding {
  public static final String CASH_WEB_CLIENT = "CashWebClient";

  @Bean(CASH_WEB_CLIENT)
  public WebClient getCashWebClient(CashClientProps props,
                                    DeferringLoadBalancerExchangeFilterFunction<LoadBalancedExchangeFilterFunction> exchangeFilterFunction)
      throws NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl(), exchangeFilterFunction);
  }

  @Bean
  public CashClient cashClient(@Qualifier(CASH_WEB_CLIENT) WebClient webClient) {
    return new CashClientImpl(webClient);
  }
}