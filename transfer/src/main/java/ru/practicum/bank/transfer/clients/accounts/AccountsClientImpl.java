package ru.practicum.bank.transfer.clients.accounts;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.bank.transfer.configs.OAuth2ConfigProps;
import ru.practicum.bank.transfer.dto.AccountsDto;
import ru.practicum.bank.transfer.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class AccountsClientImpl implements AccountsClient {
  public static final String ACCOUNT_PATH = "/account";
  public static final String ACCOUNT_ERROR_MESSAGE = "Ошибка при запросе в микросервис Accounts";
  public static final String REQUEST_ACCOUNTS_MESSAGE = "Отправлен запрос в микросервис Accounts";
  public static final String REQUEST_SUCCESS = "Запрос обработан успешно";
  public static final String BEARER = "Bearer ";

  private final WebClient webClient;
  private final ReactiveOAuth2AuthorizedClientManager manager;
  private final OAuth2ConfigProps oAuth2props;

  @Override
  public Mono<AccountsDto> requestGetAccount(String login, String currency) {
    return manager.authorize(OAuth2AuthorizeRequest
                                 .withClientRegistrationId(oAuth2props.clientRegistrationId())
                                 .principal(oAuth2props.principal())
                                 .build())
                  .flatMap(client -> {
                    var accessToken = client.getAccessToken().getTokenValue();

                    try {
                      log.info(REQUEST_ACCOUNTS_MESSAGE);

                      return webClient
                          .get()
                          .uri(uriBuilder -> uriBuilder
                              .path(ACCOUNT_PATH + "/" + login + "/" + currency)
                              .build())
                          .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                          .retrieve()
                          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
                              .bodyToMono(String.class)
                              .flatMap(error -> Mono.error(new WebClientHttpException(error))))
                          .bodyToMono(AccountsDto.class)
                          .doOnSuccess(v -> log.info(REQUEST_SUCCESS))
                          .doOnError(WebClientHttpException.class,
                                     ex -> log.error(ACCOUNT_ERROR_MESSAGE, ex));
                    } catch (WebClientHttpException e) {
                      log.error(ACCOUNT_ERROR_MESSAGE, e);
                      throw e;
                    }
                  });
  }

  @Override
  public Flux<Void> updateAccount(List<AccountsDto> accountsDto) {
    return manager.authorize(OAuth2AuthorizeRequest
                                 .withClientRegistrationId(oAuth2props.clientRegistrationId())
                                 .principal(oAuth2props.principal())
                                 .build())
                  .flatMapMany(client -> {
                    String accessToken = client.getAccessToken().getTokenValue();

                    log.info(REQUEST_ACCOUNTS_MESSAGE);

                    return Flux.fromIterable(accountsDto)
                               .flatMap(account -> webClient
                                   .patch()
                                   .uri(uriBuilder -> uriBuilder
                                       .path(ACCOUNT_PATH)
                                       .build())
                                   .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .body(BodyInserters.fromValue(account))
                                   .retrieve()
                                   .onStatus(HttpStatusCode::isError, clientResponse ->
                                       clientResponse.bodyToMono(String.class)
                                                     .flatMap(error -> Mono.error(
                                                         new WebClientHttpException(error)))
                                   )
                                   .bodyToMono(Void.class)
                                   .doOnSuccess(
                                       v -> log.info("Успешно обновлён аккаунт: {}", account))
                               )
                               .doOnComplete(() -> log.info("Все аккаунты успешно обновлены"))
                               .doOnError(WebClientHttpException.class,
                                          ex -> log.error(ACCOUNT_ERROR_MESSAGE, ex));
                  })
                  .onErrorResume(ex -> {
                    log.error("Ошибка при обновлении аккаунтов: {}", ex.getMessage());
                    return Flux.empty();
                  });
  }
}