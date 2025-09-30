package ru.practicum.bank.front.ui.clients.exchange;

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
import ru.practicum.bank.front.ui.configs.security.OAuth2ConfigProps;
import ru.practicum.bank.front.ui.dto.Rate;
import ru.practicum.bank.front.ui.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class ExchangeClientImpl implements ExchangeClient {
  public static final String BEARER = "Bearer ";

  private final WebClient webClient;
  private final ReactiveOAuth2AuthorizedClientManager manager;
  private final OAuth2ConfigProps oAuth2props;

  @Override
  public List<Rate> getRates() {
    return manager.authorize(
                      OAuth2AuthorizeRequest.withClientRegistrationId(oAuth2props.clientRegistrationId())
                                            .principal(oAuth2props.principal())
                                            .build()
                  )
                  .flatMap(client -> {
                    var accessToken = client.getAccessToken().getTokenValue();

                    return webClient
                        .get()
                        .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, clientResponse ->
                            clientResponse.bodyToMono(String.class)
                                          .map(WebClientHttpException::new)
                        )
                        .bodyToMono(new ParameterizedTypeReference<List<Rate>>() {
                        })
                        .doOnSuccess(response -> log.info("Получен ответ {}", response))
                        .doOnError(WebClientHttpException.class,
                                   ex -> log.error("Ошибка при получении списка курсов валют", ex))
                        .timeout(Duration.ofSeconds(10));
                  })
                  .blockOptional()
                  .orElseThrow(
                      () -> new WebClientHttpException("Не удалось получить список курсов валют"));
  }
}