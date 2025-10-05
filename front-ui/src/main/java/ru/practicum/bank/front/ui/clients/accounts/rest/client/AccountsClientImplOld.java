//package ru.practicum.bank.front.ui.clients.accounts.rest.client;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestClient;
//import ru.practicum.bank.front.ui.clients.accounts.AccountsClient;
//import ru.practicum.bank.front.ui.dto.UserAuthDto;
//import ru.practicum.bank.front.ui.dto.UserDto;
//import ru.practicum.bank.front.ui.dto.UserFullDto;
//import ru.practicum.bank.front.ui.exceptions.WebClientHttpException;
//
//@Slf4j
//@RequiredArgsConstructor
//public class AccountsClientImplOld implements AccountsClientOld {
//  public static final String CREATE_ACCOUNT_PATH = "/create";
//  public static final String GET_ACCOUNT_PATH = "/user";
//  public static final String GET_AUTH_PATH = "/auth";
//  public static final String ACCOUNT_ERROR_MESSAGE = "Ошибка при запросе в микросервис Accounts";
//  public static final String REQUEST_ACCOUNTS_MESSAGE = "Отправлен запрос в микросервис Accounts";
//  public static final String REQUEST_SUCCESS = "Запрос обработан успешно";
//  public static final String OAUTH2_CONFIG_BANK_PRACTICUM = "bank-practicum";
//  public static final String BEARER = "Bearer ";
//
//  private final RestClient restClient;
//
//  @Override
//  public void requestCreateUser(UserDto user) {
//
//    try {
//      log.info(REQUEST_ACCOUNTS_MESSAGE);
//
//      ResponseEntity<Void> response = restClient.post()
//                                                .uri(CREATE_ACCOUNT_PATH)
//                                                .contentType(MediaType.APPLICATION_JSON)
//                                                .body(user)
//                                                .retrieve()
//                                                .toBodilessEntity();
//
//      if (response.getStatusCode().is2xxSuccessful()) {
//        log.info(REQUEST_SUCCESS);
//      } else {
//        String errorMessage = restClient.get()
//                                        .uri("/error")
//                                        .retrieve()
//                                        .body(String.class);
//
//        throw new WebClientHttpException(errorMessage);
//      }
//    } catch (Exception e) {
//      log.error(ACCOUNT_ERROR_MESSAGE, e);
//      throw e;
//    }
//  }
//
//  @Override
//  public void requestEditUser(String path, UserDto user) {
//
//    try {
//      log.info(REQUEST_ACCOUNTS_MESSAGE);
//
//      ResponseEntity<Void> response = restClient.patch()
//                                                .uri(path)
//                                                .contentType(MediaType.APPLICATION_JSON)
//                                                .body(user)
//                                                .retrieve()
//                                                .toBodilessEntity();
//
//      if (response.getStatusCode().is2xxSuccessful()) {
//        log.info(REQUEST_SUCCESS);
//      } else {
//        String errorMessage = restClient.get()
//                                        .uri("/error")
//                                        .retrieve()
//                                        .body(String.class);
//
//        throw new WebClientHttpException(errorMessage);
//      }
//    } catch (Exception e) {
//      log.error(ACCOUNT_ERROR_MESSAGE, e);
//      throw e;
//    }
//  }
//
//
//  @Override
//  public UserFullDto requestGetUser(String login) {
//    try {
//      log.info(REQUEST_ACCOUNTS_MESSAGE);
//
//      ResponseEntity<UserFullDto> response = restClient
//          .get()
//          .uri(GET_ACCOUNT_PATH + "/" + login)
//          .retrieve()
//          .toEntity(UserFullDto.class);
//
//      if (response.getStatusCode().is2xxSuccessful()) {
//        log.info(REQUEST_SUCCESS);
//        return response.getBody();
//      } else {
//        String errorMessage = restClient
//            .get()
//            .uri("/error")
//            .retrieve()
//            .body(String.class);
//
//        throw new WebClientHttpException(errorMessage);
//      }
//    } catch (Exception e) {
//      log.error(ACCOUNT_ERROR_MESSAGE, e);
//      throw e;
//    }
//  }
//
//
//  @Override
//  public UserAuthDto requestGetAuthUser(String login) {
//
//    try {
//      log.info(REQUEST_ACCOUNTS_MESSAGE);
//
//      ResponseEntity<UserAuthDto> response = restClient
//          .get()
//          .uri(GET_AUTH_PATH + "/" + login)
//          .retrieve()
//          .toEntity(UserAuthDto.class);
//
//      if (response.getStatusCode().is2xxSuccessful()) {
//        log.info(REQUEST_SUCCESS);
//        return response.getBody();
//      } else {
//        String errorMessage = restClient
//            .get()
//            .uri("/error")
//            .retrieve()
//            .body(String.class);
//
//        throw new WebClientHttpException(errorMessage);
//      }
//    } catch (Exception e) {
//      log.error(ACCOUNT_ERROR_MESSAGE, e);
//      throw e;
//    }
//  }
//}