package ru.practicum.bank.exchange.generator.clients.exchange;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.bank.exchange.generator.dto.RateDto;
import ru.practicum.bank.exchange.generator.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class ExchangeClientImpl implements ExchangeClient {
  public static final String EXCHANGE_ERROR_MESSAGE = "Ошибка при запросе в микросервис Exchange";

  private final WebClient webClient;

  @Override
  public Flux<Void> postRates(List<RateDto> rateDtos) {
    try {
      log.info("Отправляется запрос в сервис Exchange с обновленными курсами валют");

      return Flux.fromIterable(rateDtos)
                 .flatMap(rateDto -> webClient
                     .post()
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(BodyInserters.fromValue(rateDto))
                     .retrieve()
                     .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
                         .bodyToMono(String.class)
                         .flatMap(error -> Mono.error(new WebClientHttpException(error))))
                     .bodyToMono(Void.class)
                     .doOnSuccess(v -> log.info("Успешно отправлен курс валюты: {}", rateDto))
                     .doOnError(WebClientHttpException.class,
                                ex -> log.error(EXCHANGE_ERROR_MESSAGE, ex))
                 )
                 .doOnComplete(() -> log.info("Все курсы валют обновлены"))
                 .doOnError(WebClientHttpException.class,
                            ex -> log.error(EXCHANGE_ERROR_MESSAGE, ex));
    } catch (WebClientHttpException e) {
      log.error(EXCHANGE_ERROR_MESSAGE, e);
      throw e;
    }
  }
}