package ru.practicum.bank.transfer.clients.exchange;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.transfer.dto.Rate;
import ru.practicum.bank.transfer.enums.CurrencyExchange;
import ru.practicum.bank.transfer.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class ExchangeClientImpl implements ExchangeClient {
  private final WebClient webClient;

  @Override
  public Rate getTransferRate(CurrencyExchange currencyExchange) {
    try {
      log.info("Отправлен запрос в сервис Exchange для получения списка курсов валют");

      return webClient
          .get()
          .uri(uriBuilder -> uriBuilder
              .path("/" + currencyExchange)
              .build())
          .retrieve()
          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
              .bodyToMono(String.class)
              .map(WebClientHttpException::new))
          .bodyToMono(Rate.class)
          .doOnSuccess(response -> log.info("Получен ответ {}", response))
          .doOnError(WebClientHttpException.class,
                     ex -> log.error("Ошибка при получении списка курсов валют", ex))
          .timeout(Duration.ofSeconds(10))
          .blockOptional()
          .orElseThrow(() -> new WebClientHttpException("Не удалось получить список курсов валют"));
    } catch (WebClientHttpException e) {
      log.error("Ошибка при получении списка курсов валют", e);
      throw e;
    }
  }
}