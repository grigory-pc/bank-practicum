package ru.practicum.bank.front.ui.configs.clients.accounts;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import ru.practicum.bank.front.ui.clients.accounts.AccountsClient;
import ru.practicum.bank.front.ui.clients.accounts.AccountsClientImpl;
import ru.practicum.bank.front.ui.configs.clients.DefaultRestClientFactory;
import ru.practicum.bank.front.ui.configs.clients.DefaultRestTemplateFactory;

@Configuration
public class AccountsClientBinding {
  public static final String ACCOUNTS_REST_CLIENT = "AccountsRestClient";

  @Bean(ACCOUNTS_REST_CLIENT)
  @LoadBalanced
  public RestTemplate getAccountsRestTemplate(AccountsClientProps props) {
    return DefaultRestTemplateFactory.getClient(
        props.connectTimeoutMs(),
        props.responseTimeoutMs(),
        props.baseUrl()
    );
  }

  @Bean
  public AccountsClient accountsClient(@Qualifier(ACCOUNTS_REST_CLIENT) RestTemplate restTemplate,
                                       OAuth2AuthorizedClientManager manager) {
    return new AccountsClientImpl(restTemplate, manager);
  }

  //  @Bean(ACCOUNTS_REST_CLIENT)
  //  @LoadBalanced
  //  public RestClient getAccountsRestClient(AccountsClientProps props) {
  //    return DefaultRestClientFactory.getClient(props.baseUrl());
  //  }
  //
  //  @Bean
  //  public AccountsClient accountsClient(@Qualifier(ACCOUNTS_REST_CLIENT) RestClient restClient) {
  //    return new AccountsClientImpl(restClient);
  //  }
}
