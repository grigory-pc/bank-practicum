package ru.practicum.bank.front.ui.configs.clients;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class DefaultRestTemplateFactory {

  private DefaultRestTemplateFactory() {
  }

  public static RestTemplate getClient(int connectTimeoutMs, long responseTimeoutMs,
                                       String baseUrl) {
    return new RestTemplateBuilder()
        .requestFactory(() -> {
          HttpComponentsClientHttpRequestFactory factory
              = new HttpComponentsClientHttpRequestFactory();
          factory.setConnectTimeout(connectTimeoutMs);
          factory.setReadTimeout((int) responseTimeoutMs);
          return factory;
        })
        .rootUri(baseUrl)
        .build();
  }
}