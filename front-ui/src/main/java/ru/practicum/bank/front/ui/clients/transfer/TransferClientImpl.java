package ru.practicum.bank.front.ui.clients.transfer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.bank.front.ui.dto.TransferDto;
import ru.practicum.bank.front.ui.exceptions.WebClientHttpException;

@Slf4j
@RequiredArgsConstructor
public class TransferClientImpl implements TransferClient {
  public static final String CASH_ERROR_MESSAGE = "Ошибка при запросе в микросервис Transfer";
  public static final String REQUEST_CASH_MESSAGE = "Отправлен запрос в микросервис Transfer";

  private final WebClient webClient;

  @Override
  public Mono<Void> requestTransfer(TransferDto transferDto) {
    try {
      log.info(REQUEST_CASH_MESSAGE);

      return webClient
          .post()
          .contentType(MediaType.APPLICATION_JSON)
          .body(BodyInserters.fromValue(transferDto))
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