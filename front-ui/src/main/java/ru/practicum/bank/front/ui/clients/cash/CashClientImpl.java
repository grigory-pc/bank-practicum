package ru.practicum.bank.front.ui.clients.cash;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.bank.front.ui.configs.security.OAuth2ConfigProps;
import ru.practicum.bank.front.ui.dto.CashDto;
import ru.practicum.bank.front.ui.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class CashClientImpl implements CashClient {
  public static final String CASH_ERROR_MESSAGE = "Ошибка при запросе в микросервис Cash";
  public static final String REQUEST_CASH_MESSAGE = "Отправлен запрос в микросервис Cash";

  public static final String BEARER = "Bearer ";

  private final WebClient webClient;
  private final ReactiveOAuth2AuthorizedClientManager manager;
  private final OAuth2ConfigProps oAuth2props;

  @Override
  public Mono<Void> requestCashOperation(CashDto cashDto) {
    return manager.authorize(OAuth2AuthorizeRequest
                                 .withClientRegistrationId(oAuth2props.clientRegistrationId())
                                 .principal(oAuth2props.principal())
                                 .build())
                  .flatMap(client -> {
                    var accessToken = client.getAccessToken().getTokenValue();

                    try {
                      log.info(REQUEST_CASH_MESSAGE);

                      return webClient
                          .post()
                          .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                          .contentType(MediaType.APPLICATION_JSON)
                          .body(BodyInserters.fromValue(cashDto))
                          .retrieve()
                          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
                              .bodyToMono(String.class)
                              .flatMap(error -> Mono.error(new WebClientHttpException(error))))
                          .bodyToMono(Void.class)
                          .doOnSuccess(v -> log.info("Запрос обработан успешно"))
                          .doOnError(WebClientHttpException.class,
                                     ex -> log.error(CASH_ERROR_MESSAGE, ex));
                    } catch (WebClientHttpException e) {
                      log.error(CASH_ERROR_MESSAGE, e);
                      throw e;
                    }
                  });
  }
}