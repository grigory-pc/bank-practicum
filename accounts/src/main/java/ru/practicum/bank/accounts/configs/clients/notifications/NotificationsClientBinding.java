package ru.practicum.bank.accounts.configs.clients.notifications;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.accounts.clients.notifications.NotificationsClient;
import ru.practicum.bank.accounts.clients.notifications.NotificationsClientImpl;
import ru.practicum.bank.accounts.exceptions.NegativeDurationException;

@Configuration
public class NotificationsClientBinding {
  public static final String NOTIFICATIONS_WEB_CLIENT = "NotificationsWebClient";

  @Bean(NOTIFICATIONS_WEB_CLIENT)
  public WebClient getNotificationsWebClient(NotificationsClientProps props)
      throws NegativeDurationException {
    return DefaultWebClientFactory.getClient(props.connectTimeoutMs(), props.responseTimeoutMs(),
                                             props.baseUrl());
  }

  @Bean
  public NotificationsClient notificationsClient(
      @Qualifier(NOTIFICATIONS_WEB_CLIENT) WebClient webClient) {
    return new NotificationsClientImpl(webClient);
  }
}