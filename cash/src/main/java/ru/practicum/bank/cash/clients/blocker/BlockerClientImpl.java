package ru.practicum.bank.cash.clients.blocker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.bank.cash.configs.security.OAuth2ConfigProps;
import ru.practicum.bank.cash.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class BlockerClientImpl implements BlockerClient {
  public static final String BLOCKER_ERROR_MESSAGE = "Ошибка при запросе в микросервис Blocker";
  public static final String REQUEST_BLOCKER_MESSAGE = "Отправлен запрос в микросервис Blocker";
  public static final String REQUEST_SUCCESS = "Запрос обработан успешно";
  public static final String BEARER = "Bearer ";

  private final WebClient webClient;
  private final ReactiveOAuth2AuthorizedClientManager manager;
  private final OAuth2ConfigProps oAuth2props;

  @Override
  public Mono<Boolean> requestBlockOperation() {
    return manager.authorize(OAuth2AuthorizeRequest
                                 .withClientRegistrationId(oAuth2props.clientRegistrationId())
                                 .principal(oAuth2props.principal())
                                 .build())
                  .flatMap(client -> {
                    var accessToken = client.getAccessToken().getTokenValue();

                    try {
                      log.info(REQUEST_BLOCKER_MESSAGE);

                      return webClient
                          .get()
                          .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                          .retrieve()
                          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
                              .bodyToMono(String.class)
                              .flatMap(error -> Mono.error(new WebClientHttpException(error))))
                          .bodyToMono(Boolean.class)
                          .doOnSuccess(v -> log.info(REQUEST_SUCCESS))
                          .doOnError(WebClientHttpException.class,
                                     ex -> log.error(BLOCKER_ERROR_MESSAGE, ex));
                    } catch (WebClientHttpException e) {
                      log.error(BLOCKER_ERROR_MESSAGE, e);
                      throw e;
                    }
                  });
  }
}