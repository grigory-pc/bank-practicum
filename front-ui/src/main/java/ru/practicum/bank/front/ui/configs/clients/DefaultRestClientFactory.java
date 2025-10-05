package ru.practicum.bank.front.ui.configs.clients;

import org.springframework.web.client.RestClient;

/**
 * Фабрика настраиваемых web-клиентов для выполнения запросов в сторонние или свои же сервисы.
 */
public class DefaultRestClientFactory {
  private DefaultRestClientFactory() {
  }

  /**
   * Настраиваемый rest-клиент с конфигурированием URL для выполнения запросов.
   *
   * @param baseUrl           URL для выполнения запросов.
   * @return Сконфигурированный HTTP-клиент для использования при выполнении запросов.
   */
  public static RestClient getClient(String baseUrl) {
    return RestClient.builder()
                     .baseUrl(baseUrl)
                     .build();
  }
}
