package ru.practicum.bank.cash.clients.accounts;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.bank.cash.dto.AccountsDto;
import ru.practicum.bank.cash.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class AccountsClientImpl implements AccountsClient {
  public static final String ACCOUNT_PATH = "/account";
  public static final String ACCOUNT_ERROR_MESSAGE = "Ошибка при запросе в микросервис Accounts";
  public static final String REQUEST_ACCOUNTS_MESSAGE = "Отправлен запрос в микросервис Accounts";
  public static final String REQUEST_SUCCESS = "Запрос обработан успешно";
  private final WebClient webClient;
  private final MeterRegistry meterRegistry;

  @Override
  public Mono<AccountsDto> requestGetAccount(String login, String currency) {
    try {
      log.info(REQUEST_ACCOUNTS_MESSAGE);

      return webClient
          .get()
          .uri(uriBuilder -> uriBuilder
              .path(ACCOUNT_PATH + "/" + login + "/" + currency)
              .build())
          .retrieve()
          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
              .bodyToMono(String.class)
              .flatMap(error -> Mono.error(new WebClientHttpException(error))))
          .bodyToMono(AccountsDto.class)
          .doOnSuccess(v -> log.info(REQUEST_SUCCESS))
          .doOnError(WebClientHttpException.class, ex -> log.error(ACCOUNT_ERROR_MESSAGE, ex));
    } catch (WebClientHttpException e) {
      log.error(ACCOUNT_ERROR_MESSAGE, e);
      throw e;
    }
  }

  @Override
  public Mono<Void> updateAccount(AccountsDto accountsDto) {
    try {
      log.info(REQUEST_ACCOUNTS_MESSAGE);

      return webClient
          .patch()
          .uri(uriBuilder -> uriBuilder
              .path(ACCOUNT_PATH)
              .build())
          .contentType(MediaType.APPLICATION_JSON)
          .body(BodyInserters.fromValue(accountsDto))
          .retrieve()
          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
              .bodyToMono(String.class)
              .flatMap(error -> Mono.error(new WebClientHttpException(error))))
          .bodyToMono(Void.class)
          .doOnSuccess(v -> log.info(REQUEST_SUCCESS))
          .doOnError(WebClientHttpException.class, ex -> {
            meterRegistry.counter("cash_error", Tags.of(accountsDto.userId().toString()))
                         .increment();
            log.error(ACCOUNT_ERROR_MESSAGE, ex);
          });
    } catch (WebClientHttpException e) {
      log.error(ACCOUNT_ERROR_MESSAGE, e);
      throw e;
    }
  }
}