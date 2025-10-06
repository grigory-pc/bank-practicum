package ru.practicum.bank.cash.clients.notifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.bank.cash.dto.CashDto;
import ru.practicum.bank.cash.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class NotificationsClientImpl implements NotificationsClient {
  public static final String CASH_NOTIFICATION_PATH = "/cash";

  public static final String NOTIFICATIONS_ERROR_MESSAGE
      = "Ошибка при запросе в микросервис Notifications";
  public static final String REQUEST_NOTIFICATIONS_MESSAGE
      = "Отправлен запрос в микросервис Notifications";
  public static final String REQUEST_SUCCESS = "Запрос обработан успешно";
  private final WebClient webClient;

  @Override
  public Mono<Void> requestCashNotifications(CashDto cashDto) {
    try {
      log.info(REQUEST_NOTIFICATIONS_MESSAGE);

      return webClient
          .post()
          .uri(uriBuilder -> uriBuilder
              .path(CASH_NOTIFICATION_PATH)
              .build())
          .contentType(MediaType.APPLICATION_JSON)
          .body(BodyInserters.fromValue(cashDto))
          .retrieve()
          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
              .bodyToMono(String.class)
              .flatMap(error -> Mono.error(new WebClientHttpException(error))))
          .bodyToMono(Void.class)
          .doOnSuccess(v -> log.info(REQUEST_SUCCESS))
          .doOnError(WebClientHttpException.class,
                     ex -> log.error(NOTIFICATIONS_ERROR_MESSAGE, ex));
    } catch (WebClientHttpException e) {
      log.error(NOTIFICATIONS_ERROR_MESSAGE, e);
      throw e;
    }
  }
}