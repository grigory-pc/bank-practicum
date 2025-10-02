package ru.practicum.bank.transfer.clients.blocker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.bank.transfer.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class BlockerClientImpl implements BlockerClient {
  public static final String BLOCKER_ERROR_MESSAGE = "Ошибка при запросе в микросервис Blocker";
  public static final String REQUEST_BLOCKER_MESSAGE = "Отправлен запрос в микросервис Blocker";
  public static final String REQUEST_SUCCESS = "Запрос обработан успешно";
  private final WebClient webClient;

  @Override
  public Mono<Boolean> requestBlockOperation() {
    try {
      log.info(REQUEST_BLOCKER_MESSAGE);

      return webClient
          .get()
          .retrieve()
          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
              .bodyToMono(String.class)
              .flatMap(error -> Mono.error(new WebClientHttpException(error))))
          .bodyToMono(Boolean.class)
          .doOnSuccess(v -> log.info(REQUEST_SUCCESS))
          .doOnError(WebClientHttpException.class, ex -> log.error(BLOCKER_ERROR_MESSAGE, ex));
    } catch (WebClientHttpException e) {
      log.error(BLOCKER_ERROR_MESSAGE, e);
      throw e;
    }
  }
}