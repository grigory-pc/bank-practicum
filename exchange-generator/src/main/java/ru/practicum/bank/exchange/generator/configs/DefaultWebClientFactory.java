package ru.practicum.bank.exchange.generator.configs;

import io.netty.channel.ChannelOption;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.cloud.client.loadbalancer.reactive.DeferringLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ru.practicum.bank.exchange.generator.exceptions.NegativeDurationException;

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
    return configureWebClientBuilder(connectTimeoutMs, responseTimeoutMs)
        .baseUrl(baseUrl)
        .filter(exchangeFilterFunction)
        .build();
  }

  /**
   * Метод для получения строителя для WebClient с предварительно настроенными таймаутами.
   *
   * @param connectTimeoutMs  Таймаут ожидания подключения в миллисекундах.
   * @param responseTimeoutMs Таймаут ожидания ответа в миллисекундах.
   * @return Предварительно настроенный {@link WebClient.Builder}.
   * @throws ru.practicum.bank.exchange.generator.exceptions.NegativeDurationException Если переданы отрицательные значения таймаутов.
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
}