package ru.practicum.bank.front.ui.configs.transfer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.front.ui.configs.DefaultWebClientFactory;
import ru.practicum.bank.front.ui.exceptions.NegativeDurationException;

@Configuration
@RequiredArgsConstructor
public class TransferClientBinding {
  private final TransferClientProps props;

  @Bean("TransferWebClient")
  public WebClient getTransferWebClient() throws NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl());
  }
}