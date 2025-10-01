package ru.practicum.bank.transfer.clients.notifications;

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
import ru.practicum.bank.transfer.configs.security.OAuth2ConfigProps;
import ru.practicum.bank.transfer.dto.TransferDto;
import ru.practicum.bank.transfer.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class NotificationsClientImpl implements NotificationsClient {
  public static final String TRANSFER_NOTIFICATION_PATH = "/transfer";

  public static final String NOTIFICATIONS_ERROR_MESSAGE
      = "Ошибка при запросе в микросервис Notifications";
  public static final String REQUEST_NOTIFICATIONS_MESSAGE
      = "Отправлен запрос в микросервис Notifications";
  public static final String REQUEST_SUCCESS = "Запрос обработан успешно";
  public static final String BEARER = "Bearer ";

  private final WebClient webClient;
  private final ReactiveOAuth2AuthorizedClientManager manager;
  private final OAuth2ConfigProps oAuth2props;

  @Override
  public Mono<Void> requestTransferNotifications(TransferDto transferDto) {
    return manager.authorize(OAuth2AuthorizeRequest
                                 .withClientRegistrationId(oAuth2props.clientRegistrationId())
                                 .principal(oAuth2props.principal())
                                 .build())
                  .flatMap(client -> {
                    var accessToken = client.getAccessToken().getTokenValue();

                    try {
                      log.info(REQUEST_NOTIFICATIONS_MESSAGE);

                      return webClient
                          .post()
                          .uri(uriBuilder -> uriBuilder
                              .path(TRANSFER_NOTIFICATION_PATH)
                              .build())
                          .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                          .contentType(MediaType.APPLICATION_JSON)
                          .body(BodyInserters.fromValue(transferDto))
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
                  });
  }
}