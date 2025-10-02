package ru.practicum.bank.front.ui.configs.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.netty.channel.ChannelOption;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.cloud.client.loadbalancer.reactive.DeferringLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ru.practicum.bank.front.ui.exceptions.NegativeDurationException;

/**
 * Фабрика настраиваемых web-клиентов для выполнения запросов в сторонние или свои же сервисы.
 */
public class DefaultWebClientFactory {
  private DefaultWebClientFactory() {
  }

  /**
   * Настраиваемый web-клиент с конфигурированием URL для выполнения запросов.
   *
   * @param connectTimeoutMs  Таймаут ожидания подключения в миллисекундах.
   * @param responseTimeoutMs Таймаут ожидания ответа в миллисекундах.
   * @param baseUrl           URL для выполнения запросов.
   * @return Сконфигурированный HTTP-клиент для использования при выполнении запросов.
   * @throws NegativeDurationException Если переданы отрицательные значения таймаутов.
   */
  public static WebClient getClient(int connectTimeoutMs, long responseTimeoutMs, String baseUrl,
                                    DeferringLoadBalancerExchangeFilterFunction<LoadBalancedExchangeFilterFunction> exchangeFilterFunction)
      throws NegativeDurationException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    return configureWebClientBuilder(connectTimeoutMs, responseTimeoutMs)
        .baseUrl(baseUrl)
        .filter(exchangeFilterFunction)
        .exchangeStrategies(getExchangeStrategies())
        .codecs((configurer -> configurer.customCodecs()
                                         .register(new Jackson2JsonDecoder(objectMapper))))
        .build();
  }

  /**
   * Метод для получения строителя для WebClient с предварительно настроенными таймаутами.
   *
   * @param connectTimeoutMs  Таймаут ожидания подключения в миллисекундах.
   * @param responseTimeoutMs Таймаут ожидания ответа в миллисекундах.
   * @return Предварительно настроенный {@link WebClient.Builder}.
   * @throws NegativeDurationException Если переданы отрицательные значения таймаутов.
   */
  private static WebClient.Builder configureWebClientBuilder(int connectTimeoutMs,
                                                             long responseTimeoutMs)
      throws NegativeDurationException {
    if (connectTimeoutMs <= 0 || responseTimeoutMs <= 0) {
      throw new NegativeDurationException("Таймауты должны быть положительными значениями");
    }

    HttpClient baseClient = HttpClient
        .create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMs)
        .responseTimeout(Duration.of(responseTimeoutMs, ChronoUnit.MILLIS));

    return WebClient
        .builder()
        .clientConnector(new ReactorClientHttpConnector(baseClient));
  }

  private static ExchangeStrategies getExchangeStrategies() {
    ObjectMapper objectMapper = new ObjectMapper();

    return ExchangeStrategies.builder()
                             .codecs(clientDefaultCodecsConfigurer -> {
                               clientDefaultCodecsConfigurer
                                   .defaultCodecs()
                                   .jackson2JsonEncoder(
                                       new Jackson2JsonEncoder(objectMapper,
                                                               MediaType.APPLICATION_JSON,
                                                               MediaType.TEXT_EVENT_STREAM));
                               clientDefaultCodecsConfigurer
                                   .defaultCodecs()
                                   .jackson2JsonDecoder(
                                       new Jackson2JsonDecoder(objectMapper,
                                                               MediaType.APPLICATION_JSON,
                                                               MediaType.TEXT_EVENT_STREAM));
                             }).build();
  }
}
