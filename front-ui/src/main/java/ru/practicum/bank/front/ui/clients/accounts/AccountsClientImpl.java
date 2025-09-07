package ru.practicum.bank.front.ui.clients.accounts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.bank.front.ui.dto.UserDto;
import ru.practicum.bank.front.ui.exceptions.WebClientHttpException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountsClientImpl implements AccountsClient {
  public static final String CREATE_ACCOUNT_PATH = "/create";

  public static final String ACCOUNT_ERROR_MESSAGE = "Ошибка при запросе в микросервис Accounts";
  public static final String REQUEST_ACCOUNTS_MESSAGE = "Отправлен запрос в микросервис Accounts";

  @Qualifier("AccountsWebClient")
  private final WebClient webClient;

  @Override
  public Mono<Void> requestCreateUser(UserDto user) {
    try {
      log.info(REQUEST_ACCOUNTS_MESSAGE);

      return webClient
          .post()
          .uri(uriBuilder -> uriBuilder
              .path(CREATE_ACCOUNT_PATH)
              .build())
          .contentType(MediaType.APPLICATION_JSON)
          .body(BodyInserters.fromValue(user))
          .retrieve()
          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
              .bodyToMono(String.class)
              .flatMap(error -> Mono.error(new WebClientHttpException(error))))
          .bodyToMono(Void.class)
          .doOnSuccess(v -> log.info("Запрос обработан успешно"))
          .doOnError(WebClientHttpException.class, ex -> log.error(ACCOUNT_ERROR_MESSAGE, ex));
    } catch (WebClientHttpException e) {
      log.error(ACCOUNT_ERROR_MESSAGE, e);
      throw e;
    }
  }

  @Override
  public Mono<Void> requestEditUser(String path, UserDto user) {
    try {
      log.info(REQUEST_ACCOUNTS_MESSAGE);

      return webClient
          .patch()
          .uri(uriBuilder -> uriBuilder
              .path(path)
              .build())
          .contentType(MediaType.APPLICATION_JSON)
          .body(BodyInserters.fromValue(user))
          .retrieve()
          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
              .bodyToMono(String.class)
              .flatMap(error -> Mono.error(new WebClientHttpException(error))))
          .bodyToMono(Void.class)
          .doOnSuccess(v -> log.info("Запрос обработан успешно"))
          .doOnError(WebClientHttpException.class, ex -> log.error(ACCOUNT_ERROR_MESSAGE, ex));
    } catch (WebClientHttpException e) {
      log.error(ACCOUNT_ERROR_MESSAGE, e);
      throw e;
    }
  }
}