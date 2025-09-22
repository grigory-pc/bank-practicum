package ru.practicum.bank.transfer.clients.accounts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.bank.transfer.dto.AccountRequestDto;
import ru.practicum.bank.transfer.dto.AccountsDto;

@Slf4j
@RequiredArgsConstructor
public class AccountsClientImpl implements AccountsClient {
  //  public static final String CREATE_ACCOUNT_PATH = "/create";
  //  public static final String GET_ACCOUNT_PATH = "/user";
  //  public static final String GET_AUTH_PATH = "/auth";
  public static final String ACCOUNT_ERROR_MESSAGE = "Ошибка при запросе в микросервис Accounts";
  public static final String REQUEST_ACCOUNTS_MESSAGE = "Отправлен запрос в микросервис Accounts";
  public static final String REQUEST_SUCCESS = "Запрос обработан успешно";
  private final WebClient webClient;

  @Override
  public Mono<AccountsDto> requestGetAccount(AccountRequestDto accountRequest) {
    try {
      log.info(REQUEST_ACCOUNTS_MESSAGE);

      return webClient
          .get()
          .uri(uriBuilder -> uriBuilder
              .path(GET_ACCOUNT_PATH + "/" + login)
              .build()).retrieve()
          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
              .bodyToMono(String.class)
              .flatMap(error -> Mono.error(new WebClientHttpException(error))))
          .bodyToMono(UserFullDto.class)
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
          .doOnSuccess(v -> log.info(REQUEST_SUCCESS))
          .doOnError(WebClientHttpException.class, ex -> log.error(ACCOUNT_ERROR_MESSAGE, ex));
    } catch (WebClientHttpException e) {
      log.error(ACCOUNT_ERROR_MESSAGE, e);
      throw e;
    }
  }
}