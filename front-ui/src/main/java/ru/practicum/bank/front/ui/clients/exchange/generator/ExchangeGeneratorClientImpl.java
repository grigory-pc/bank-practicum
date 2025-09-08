package ru.practicum.bank.front.ui.clients.exchange.generator;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.bank.front.ui.dto.Rate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeGeneratorClientImpl implements ExchangeGeneratorClient {
  private final WebClient webClient;

  @Override
  public List<Rate> getRates() {
//    try {
      log.info("Отправлен запрос в сервис ExchangeGenerator");
      return List.of(new Rate("RUB", "Рубли", 80));

//      return webClient
//          .get()
//          .retrieve()
//          .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
//              .bodyToMono(String.class)
//              .map(ExchangeGeneratorHttpException::new))
//          .bodyToMono(new ParameterizedTypeReference<List<Rate>>() {
//          })
//          .doOnSuccess(response -> log.info("Получен ответ {}", response))
//          .doOnError(ExchangeGeneratorHttpException.class,
//                     ex -> log.error("Ошибка при получении курсов валют", ex))
//          .timeout(Duration.ofSeconds(10))
//          .blockOptional()
//          .orElseThrow(() -> new ExchangeGeneratorHttpException("Не удалось получить курсы валют"));
//    } catch (ExchangeGeneratorHttpException e) {
//      log.error("Ошибка при получении курсов валют", e);
//      throw e;
//    }
  }
}