package ru.practicum.bank.front.ui.configs.clients.transfer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.reactive.DeferringLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.front.ui.clients.transfer.TransferClient;
import ru.practicum.bank.front.ui.clients.transfer.TransferClientImpl;
import ru.practicum.bank.front.ui.configs.clients.DefaultWebClientFactory;
import ru.practicum.bank.front.ui.configs.security.OAuth2ConfigProps;
import ru.practicum.bank.front.ui.exceptions.NegativeDurationException;

@Configuration
public class TransferClientBinding {
  public static final String TRANSFER_WEB_CLIENT = "TransferWebClient";

  @Bean(TRANSFER_WEB_CLIENT)
  public WebClient getTransferWebClient(TransferClientProps props,
                                        DeferringLoadBalancerExchangeFilterFunction<LoadBalancedExchangeFilterFunction> exchangeFilterFunction)
      throws NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl(), exchangeFilterFunction);
  }

  @Bean
  public TransferClient transferClient(@Qualifier(TRANSFER_WEB_CLIENT) WebClient webClient,
                                       ReactiveOAuth2AuthorizedClientManager clientManager,
                                       OAuth2ConfigProps oAuth2Props) {
    return new TransferClientImpl(webClient, clientManager, oAuth2Props);
  }
}