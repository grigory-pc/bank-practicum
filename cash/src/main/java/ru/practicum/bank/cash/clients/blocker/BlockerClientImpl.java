package ru.practicum.bank.cash.clients.blocker;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.bank.cash.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class BlockerClientImpl implements BlockerClient {
  public static final String BLOCKER_ERROR_MESSAGE = "Ошибка при запросе в микросервис Blocker";
  public static final String REQUEST_BLOCKER_MESSAGE = "Отправлен запрос в микросервис Blocker";
  public static final String REQUEST_SUCCESS = "Запрос обработан успешно";
  private final WebClient webClient;
  private final MeterRegistry meterRegistry;

  @Override
  public Mono<Boolean> requestBlockOperation(String login) {
    try {
      log.info(REQUEST_BLOCKER_MESSAGE);

      return webClient
          .get()
          .retrieve()
          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
              .bodyToMono(String.class)
              .flatMap(error -> Mono.error(new WebClientHttpException(error))))
          .bodyToMono(Boolean.class)
          .doOnSuccess(v -> {
            meterRegistry.counter("block_operation", Tags.of("login", login)).increment();
            log.info(REQUEST_SUCCESS);
          })
          .doOnError(WebClientHttpException.class, ex -> log.error(BLOCKER_ERROR_MESSAGE, ex));
    } catch (WebClientHttpException e) {
      log.error(BLOCKER_ERROR_MESSAGE, e);
      throw e;
    }
  }
}