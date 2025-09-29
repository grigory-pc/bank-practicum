package ru.practicum.bank.front.ui.clients.cash;

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
import ru.practicum.bank.front.ui.dto.CashDto;
import ru.practicum.bank.front.ui.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class CashClientImpl implements CashClient {
  public static final String CASH_ERROR_MESSAGE = "Ошибка при запросе в микросервис Cash";
  public static final String REQUEST_CASH_MESSAGE = "Отправлен запрос в микросервис Cash";

  private final WebClient webClient;
  private final OAuth2AuthorizedClientManager manager;

  @Override
  public Mono<Void> requestCashOperation(CashDto cashDto) {
    OAuth2AuthorizedClient oAuth2client = manager.authorize(OAuth2AuthorizeRequest
                                                                .withClientRegistrationId("bank-practicum")
                                                                .principal("system")
                                                                .build()
    );

    var accessToken = oAuth2client.getAccessToken().getTokenValue();

    try {
      log.info(REQUEST_CASH_MESSAGE);

      return webClient
          .post()
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
          .contentType(MediaType.APPLICATION_JSON)
          .body(BodyInserters.fromValue(cashDto))
          .retrieve()
          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
              .bodyToMono(String.class)
              .flatMap(error -> Mono.error(new WebClientHttpException(error))))
          .bodyToMono(Void.class)
          .doOnSuccess(v -> log.info("Запрос обработан успешно"))
          .doOnError(WebClientHttpException.class, ex -> log.error(CASH_ERROR_MESSAGE, ex));
    } catch (WebClientHttpException e) {
      log.error(CASH_ERROR_MESSAGE, e);
      throw e;
    }
  }
}