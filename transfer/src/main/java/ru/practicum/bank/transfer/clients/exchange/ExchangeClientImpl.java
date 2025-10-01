package ru.practicum.bank.transfer.clients.exchange;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.transfer.configs.security.OAuth2ConfigProps;
import ru.practicum.bank.transfer.dto.Rate;
import ru.practicum.bank.transfer.enums.CurrencyExchange;
import ru.practicum.bank.transfer.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class ExchangeClientImpl implements ExchangeClient {
  public static final String BEARER = "Bearer ";

  private final WebClient webClient;
  private final ReactiveOAuth2AuthorizedClientManager manager;
  private final OAuth2ConfigProps oAuth2props;

  @Override
  public Rate getTransferRate(CurrencyExchange currencyExchange) {
    return manager.authorize(OAuth2AuthorizeRequest
                                 .withClientRegistrationId(oAuth2props.clientRegistrationId())
                                 .principal(oAuth2props.principal())
                                 .build())
                  .flatMap(client -> {
                    var accessToken = client.getAccessToken().getTokenValue();

                    try {
                      log.info(
                          "Отправлен запрос в сервис Exchange для получения списка курсов валют");

                      return webClient
                          .get()
                          .uri(uriBuilder -> uriBuilder
                              .path("/" + currencyExchange)
                              .build())
                          .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                          .retrieve()
                          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
                              .bodyToMono(String.class)
                              .map(WebClientHttpException::new))
                          .bodyToMono(Rate.class)
                          .doOnSuccess(response -> log.info("Получен ответ {}", response))
                          .doOnError(WebClientHttpException.class,
                                     ex -> log.error("Ошибка при получении списка курсов валют",
                                                     ex))
                          .timeout(Duration.ofSeconds(10));
                    } catch (WebClientHttpException e) {
                      log.error("Ошибка при получении списка курсов валют", e);
                      throw e;
                    }
                  }).blockOptional()
                  .orElseThrow(
                      () -> new WebClientHttpException("Не удалось получить список курсов валют"));
  }
}