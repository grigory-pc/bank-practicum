package ru.practicum.bank.exchange.clients;

import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.bank.exchange.configs.security.OAuth2ConfigProps;
import ru.practicum.bank.exchange.dto.Rate;
import ru.practicum.bank.exchange.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class ExchangeGeneratorClientImpl implements ExchangeGeneratorClient {
  public static final String BEARER = "Bearer ";

  private final WebClient webClient;
  private final ReactiveOAuth2AuthorizedClientManager manager;
  private final OAuth2ConfigProps oAuth2props;

  @Override
  public Mono<List<Rate>> getRates() {
    return manager.authorize(OAuth2AuthorizeRequest
                                 .withClientRegistrationId(oAuth2props.clientRegistrationId())
                                 .principal(oAuth2props.principal())
                                 .build())
                  .flatMap(client -> {
                    var accessToken = client.getAccessToken().getTokenValue();

                    try {
                      log.info("Отправлен запрос в сервис ExchangeGenerator");

                      return webClient
                          .get()
                          .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                          .retrieve()
                          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
                              .bodyToMono(String.class)
                              .map(WebClientHttpException::new))
                          .bodyToMono(new ParameterizedTypeReference<List<Rate>>() {
                          })
                          .doOnSuccess(response -> log.info("Получен ответ {}", response))
                          .doOnError(WebClientHttpException.class,
                                     ex -> log.error("Ошибка при получении курсов валют", ex))
                          .timeout(Duration.ofSeconds(10));
                    } catch (WebClientHttpException e) {
                      log.error("Ошибка при получении курсов валют", e);
                      throw e;
                    }
                  });
  }

  @Override
  public Mono<Rate> getCurrencyRate(String currencyExchange) {
    return manager.authorize(OAuth2AuthorizeRequest
                                 .withClientRegistrationId(oAuth2props.clientRegistrationId())
                                 .principal(oAuth2props.principal())
                                 .build())
                  .flatMap(client -> {
                    var accessToken = client.getAccessToken().getTokenValue();

                    try {
                      log.info("Отправлен запрос в сервис ExchangeGenerator");

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
                                     ex -> log.error("Ошибка при получении курса валюты", ex));
                    } catch (WebClientHttpException e) {
                      log.error("Ошибка при получении курса валюты", e);
                      throw e;
                    }
                  });
  }
}