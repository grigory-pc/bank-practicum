package ru.practicum.bank.front.ui.clients.accounts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.bank.front.ui.dto.UserAuthDto;
import ru.practicum.bank.front.ui.dto.UserDto;
import ru.practicum.bank.front.ui.dto.UserFullDto;
import ru.practicum.bank.front.ui.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class AccountsClientImpl implements AccountsClient {
  public static final String CREATE_ACCOUNT_PATH = "/create";
  public static final String GET_ACCOUNT_PATH = "/user";
  public static final String GET_AUTH_PATH = "/auth";
  public static final String ACCOUNT_ERROR_MESSAGE = "Ошибка при запросе в микросервис Accounts";
  public static final String REQUEST_ACCOUNTS_MESSAGE = "Отправлен запрос в микросервис Accounts";
  public static final String REQUEST_SUCCESS = "Запрос обработан успешно";
  public static final String OAUTH2_CONFIG_BANK_PRACTICUM = "bank-practicum";
  public static final String BEARER = "Bearer ";

  private final WebClient webClient;
  private final OAuth2AuthorizedClientManager manager;

  @Override
  public Mono<Void> requestCreateUser(UserDto user) {
    OAuth2AuthorizedClient oAuth2client = manager.authorize(OAuth2AuthorizeRequest
                                                                .withClientRegistrationId("bank-practicum")
                                                                .principal("system")
                                                                .build()
    );

    var accessToken = oAuth2client.getAccessToken().getTokenValue();

    try {
      log.info(REQUEST_ACCOUNTS_MESSAGE);

      return webClient
          .post()
          .uri(uriBuilder -> uriBuilder
              .path(CREATE_ACCOUNT_PATH)
              .build())
          .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
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

  @Override
  public Mono<Void> requestEditUser(String path, UserDto user) {
    OAuth2AuthorizedClient oAuth2client = manager.authorize(OAuth2AuthorizeRequest
                                                                .withClientRegistrationId("bank-practicum")
                                                                .principal("system")
                                                                .build()
    );

    var accessToken = oAuth2client.getAccessToken().getTokenValue();

    try {
      log.info(REQUEST_ACCOUNTS_MESSAGE);

      return webClient
          .patch()
          .uri(uriBuilder -> uriBuilder
              .path(path)
              .build())
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
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

  @Override
  public Mono<UserFullDto> requestGetUser(String login) {
    OAuth2AuthorizedClient oAuth2client = manager.authorize(OAuth2AuthorizeRequest
                                                                .withClientRegistrationId("bank-practicum")
                                                                .principal("system")
                                                                .build()
    );

    var accessToken = oAuth2client.getAccessToken().getTokenValue();

    try {
      log.info(REQUEST_ACCOUNTS_MESSAGE);

      return webClient
          .get()
          .uri(uriBuilder -> uriBuilder
              .path(GET_ACCOUNT_PATH + "/" + login)
              .build())
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
          .retrieve()
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
  public Mono<UserAuthDto> requestGetAuthUser(String login) {
    OAuth2AuthorizedClient oAuth2client = manager.authorize(OAuth2AuthorizeRequest
                                                                .withClientRegistrationId(
                                                                    OAUTH2_CONFIG_BANK_PRACTICUM)
                                                                .principal("system")
                                                                .build()
    );

    var accessToken = oAuth2client.getAccessToken().getTokenValue();

    try {
      log.info(REQUEST_ACCOUNTS_MESSAGE);

      return webClient
          .get()
          .uri(uriBuilder -> uriBuilder
              .path(GET_AUTH_PATH + "/" + login)
              .build())
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
          .retrieve()
          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
              .bodyToMono(String.class)
              .flatMap(error -> Mono.error(new WebClientHttpException(error))))
          .bodyToMono(UserAuthDto.class)
          .doOnSuccess(v -> log.info(REQUEST_SUCCESS))
          .doOnError(WebClientHttpException.class, ex -> log.error(ACCOUNT_ERROR_MESSAGE, ex));
    } catch (WebClientHttpException e) {
      log.error(ACCOUNT_ERROR_MESSAGE, e);
      throw e;
    }
  }
}