package ru.practicum.bank.cash.configs.clients.blocker;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.cash.clients.blocker.BlockerClient;
import ru.practicum.bank.cash.clients.blocker.BlockerClientImpl;
import ru.practicum.bank.cash.configs.DefaultWebClientFactory;
import ru.practicum.bank.cash.exceptions.NegativeDurationException;

@Configuration
public class BlockerClientBinding {
  public static final String BLOCKER_WEB_CLIENT = "BlockerWebClient";

  @Bean(BLOCKER_WEB_CLIENT)
  public WebClient getBlockerWebClient(BlockerClientProps props) throws
                                                                   NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl());
  }

  @Bean
  public BlockerClient blockerClient(@Qualifier(BLOCKER_WEB_CLIENT) WebClient webClient) {
    return new BlockerClientImpl(webClient);
  }
}