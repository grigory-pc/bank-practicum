package ru.practicum.bank.front.ui.configs.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.front.ui.clients.accounts.AccountsClient;
import ru.practicum.bank.front.ui.clients.accounts.AccountsClientImpl;
import ru.practicum.bank.front.ui.configs.DefaultWebClientFactory;
import ru.practicum.bank.front.ui.exceptions.NegativeDurationException;

@Configuration
@RequiredArgsConstructor
public class AccountsClientBinding {
  public static final String ACCOUNTS_WEB_CLIENT = "AccountsWebClient";
  private final AccountsClientProps props;

  @Bean(ACCOUNTS_WEB_CLIENT)
  public WebClient getAccountsWebClient() throws NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl());
  }

  @Bean
  public AccountsClient accountsClient(@Qualifier(ACCOUNTS_WEB_CLIENT) WebClient webClient) {
    return new AccountsClientImpl(webClient);
  }
}