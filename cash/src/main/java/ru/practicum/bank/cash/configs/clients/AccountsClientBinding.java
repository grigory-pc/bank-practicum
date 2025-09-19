package ru.practicum.bank.cash.configs.clients;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.cash.clients.AccountsClient;
import ru.practicum.bank.cash.clients.AccountsClientImpl;
import ru.practicum.bank.cash.configs.DefaultWebClientFactory;
import ru.practicum.bank.cash.exceptions.NegativeDurationException;

@Configuration
public class AccountsClientBinding {
  public static final String ACCOUNTS_WEB_CLIENT = "AccountsWebClient";

  @Bean(ACCOUNTS_WEB_CLIENT)
  public WebClient getAccountsWebClient(AccountsClientProps props) throws
                                                                   NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl());
  }

  @Bean
  public AccountsClient accountsClient(@Qualifier(ACCOUNTS_WEB_CLIENT) WebClient webClient) {
    return new AccountsClientImpl(webClient);
  }
}