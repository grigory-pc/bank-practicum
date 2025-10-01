package ru.practicum.bank.cash.configs.clients.blocker;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.reactive.DeferringLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.cash.clients.blocker.BlockerClient;
import ru.practicum.bank.cash.clients.blocker.BlockerClientImpl;
import ru.practicum.bank.cash.configs.clients.DefaultWebClientFactory;
import ru.practicum.bank.cash.configs.security.OAuth2ConfigProps;
import ru.practicum.bank.cash.exceptions.NegativeDurationException;

@Configuration
public class BlockerClientBinding {
  public static final String BLOCKER_WEB_CLIENT = "BlockerWebClient";

  @Bean(BLOCKER_WEB_CLIENT)
  public WebClient getBlockerWebClient(BlockerClientProps props,
                                       DeferringLoadBalancerExchangeFilterFunction<LoadBalancedExchangeFilterFunction> exchangeFilterFunction)
      throws NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl(), exchangeFilterFunction);
  }

  @Bean
  public BlockerClient blockerClient(@Qualifier(BLOCKER_WEB_CLIENT) WebClient webClient,
                                     ReactiveOAuth2AuthorizedClientManager clientManager,
                                     OAuth2ConfigProps oAuth2Props) {
    return new BlockerClientImpl(webClient, clientManager, oAuth2Props);
  }
}