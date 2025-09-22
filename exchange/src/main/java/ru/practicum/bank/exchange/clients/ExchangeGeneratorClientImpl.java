package ru.practicum.bank.exchange.clients;

import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.exchange.dto.Rate;
import ru.practicum.bank.exchange.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class ExchangeGeneratorClientImpl implements ExchangeGeneratorClient {
  private final WebClient webClient;

  @Override
  public List<Rate> getRates() {
    try {
      log.info("Отправлен запрос в сервис ExchangeGenerator");

      return webClient
          .get()
          .retrieve()
          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
              .bodyToMono(String.class)
              .map(WebClientHttpException::new))
          .bodyToMono(new ParameterizedTypeReference<List<Rate>>() {
          })
          .doOnSuccess(response -> log.info("Получен ответ {}", response))
          .doOnError(WebClientHttpException.class,
                     ex -> log.error("Ошибка при получении курсов валют", ex))
          .timeout(Duration.ofSeconds(10))
          .blockOptional()
          .orElseThrow(() -> new WebClientHttpException("Не удалось получить курсы валют"));
    } catch (WebClientHttpException e) {
      log.error("Ошибка при получении курсов валют", e);
      throw e;
    }
  }
}