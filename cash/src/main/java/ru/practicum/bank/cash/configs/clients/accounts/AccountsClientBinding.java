package ru.practicum.bank.cash.configs.clients.accounts;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.cash.clients.accounts.AccountsClient;
import ru.practicum.bank.cash.clients.accounts.AccountsClientImpl;
import ru.practicum.bank.cash.configs.clients.DefaultWebClientFactory;
import ru.practicum.bank.cash.exceptions.NegativeDurationException;

@Configuration
public class AccountsClientBinding {
  public static final String ACCOUNTS_WEB_CLIENT = "AccountsWebClient";

  @Bean(ACCOUNTS_WEB_CLIENT)
  public WebClient getAccountsWebClient(AccountsClientProps props,
                                        ObservationRegistry observationRegistry)
      throws NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl(), observationRegistry);
  }

  @Bean
  public AccountsClient accountsClient(@Qualifier(ACCOUNTS_WEB_CLIENT) WebClient webClient,
                                       MeterRegistry meterRegistry) {
    return new AccountsClientImpl(webClient, meterRegistry);
  }
}