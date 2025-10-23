package ru.practicum.bank.front.ui.configs.clients.transfer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.front.ui.clients.transfer.TransferClient;
import ru.practicum.bank.front.ui.clients.transfer.TransferClientImpl;
import ru.practicum.bank.front.ui.configs.clients.DefaultWebClientFactory;
import ru.practicum.bank.front.ui.exceptions.NegativeDurationException;

@Configuration
public class TransferClientBinding {
  public static final String TRANSFER_WEB_CLIENT = "TransferWebClient";

  @Bean(TRANSFER_WEB_CLIENT)
  public WebClient getTransferWebClient(TransferClientProps props)
      throws NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl());
  }

  @Bean
  public TransferClient transferClient(@Qualifier(TRANSFER_WEB_CLIENT) WebClient webClient) {
    return new TransferClientImpl(webClient);
  }
}