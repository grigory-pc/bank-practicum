package ru.practicum.bank.front.ui.clients.accounts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
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
  public static final String OAUTH2_CONFIG = "bank-practicum";
  public static final String OAUTH2_PRINCIPAL = "system";

  private final RestTemplate restTemplate;
  private final OAuth2AuthorizedClientManager oAuth2ClientManager;

  @Override
  public void requestCreateUser(UserDto user) {
    log.info(REQUEST_ACCOUNTS_MESSAGE);

    OAuth2AuthorizedClient client = oAuth2ClientManager.authorize(OAuth2AuthorizeRequest
                                                                      .withClientRegistrationId(
                                                                          OAUTH2_CONFIG)
                                                                      .principal(OAUTH2_PRINCIPAL)
                                                                      .build()
    );

    String accessToken = client.getAccessToken().getTokenValue();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<UserDto> entity = new HttpEntity<>(user, headers);

    try {
      restTemplate.postForEntity(
          CREATE_ACCOUNT_PATH,
          user,
          Void.class,
          entity
      );

      log.info(REQUEST_SUCCESS);
    } catch (RestClientException e) {
      log.error(ACCOUNT_ERROR_MESSAGE, e);
      throw new WebClientHttpException(e.getMessage());
    }
  }

  @Override
  public void requestEditUser(String path, UserDto user) {
    log.info(REQUEST_ACCOUNTS_MESSAGE);

    try {
      restTemplate.patchForObject(
          path,
          user,
          Void.class
      );

      log.info(REQUEST_SUCCESS);
    } catch (RestClientException e) {
      log.error(ACCOUNT_ERROR_MESSAGE, e);
      throw new WebClientHttpException(e.getMessage());
    }
  }

  @Override
  public UserFullDto requestGetUser(String login) {
    log.info(REQUEST_ACCOUNTS_MESSAGE);

    OAuth2AuthorizedClient client = oAuth2ClientManager.authorize(OAuth2AuthorizeRequest
                                                                      .withClientRegistrationId(
                                                                          OAUTH2_CONFIG)
                                                                      .principal(OAUTH2_PRINCIPAL)
                                                                      .build()
    );

    String accessToken = client.getAccessToken().getTokenValue();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<Void> entity = new HttpEntity<>(headers);

    try {
      ResponseEntity<UserFullDto> response = restTemplate.getForEntity(
          GET_ACCOUNT_PATH + "/" + login,
          UserFullDto.class,
          entity
      );

      log.info(REQUEST_SUCCESS);
      return response.getBody();
    } catch (RestClientException e) {
      log.error(ACCOUNT_ERROR_MESSAGE, e);
      throw new WebClientHttpException(e.getMessage());
    }
  }

  @Override
  public UserAuthDto requestGetAuthUser(String login) {
    log.info(REQUEST_ACCOUNTS_MESSAGE);

    OAuth2AuthorizedClient client = oAuth2ClientManager.authorize(OAuth2AuthorizeRequest
                                                                      .withClientRegistrationId(
                                                                          OAUTH2_CONFIG)
                                                                      .principal(OAUTH2_PRINCIPAL)
                                                                      .build()
    );

    String accessToken = client.getAccessToken().getTokenValue();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<Void> entity = new HttpEntity<>(headers);

    try {
      ResponseEntity<UserAuthDto> response = restTemplate.getForEntity(
          GET_AUTH_PATH + "/" + login,
          UserAuthDto.class,
          entity
      );

      log.info(REQUEST_SUCCESS);
      return response.getBody();
    } catch (RestClientException e) {
      log.error(ACCOUNT_ERROR_MESSAGE, e);
      throw new WebClientHttpException(e.getMessage());
    }
  }
}