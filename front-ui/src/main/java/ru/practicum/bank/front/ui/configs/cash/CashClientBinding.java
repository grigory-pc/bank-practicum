package ru.practicum.bank.front.ui.configs.cash;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.front.ui.clients.cash.CashClient;
import ru.practicum.bank.front.ui.clients.cash.CashClientImpl;
import ru.practicum.bank.front.ui.configs.DefaultWebClientFactory;
import ru.practicum.bank.front.ui.exceptions.NegativeDurationException;

@Configuration
@RequiredArgsConstructor
public class CashClientBinding {
  public static final String CASH_WEB_CLIENT = "CashWebClient";
  private final CashClientProps props;

  @Bean(CASH_WEB_CLIENT)
  public WebClient getCashWebClient() throws NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl());
  }

  @Bean
  public CashClient cashClient(@Qualifier(CASH_WEB_CLIENT) WebClient webClient) {
    return new CashClientImpl(webClient);
  }
}