package ru.practicum.bank.cash.configs.clients.blocker;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.cash.clients.blocker.BlockerClient;
import ru.practicum.bank.cash.clients.blocker.BlockerClientImpl;
import ru.practicum.bank.cash.configs.clients.DefaultWebClientFactory;
import ru.practicum.bank.cash.exceptions.NegativeDurationException;

@Configuration
public class BlockerClientBinding {
  public static final String BLOCKER_WEB_CLIENT = "BlockerWebClient";

  @Bean(BLOCKER_WEB_CLIENT)
  public WebClient getBlockerWebClient(BlockerClientProps props,
                                       ObservationRegistry observationRegistry)
      throws NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl(), observationRegistry);
  }

  @Bean
  public BlockerClient blockerClient(@Qualifier(BLOCKER_WEB_CLIENT) WebClient webClient,
                                     MeterRegistry meterRegistry) {
    return new BlockerClientImpl(webClient, meterRegistry);
  }
}