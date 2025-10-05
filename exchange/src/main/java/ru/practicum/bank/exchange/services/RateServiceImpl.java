package ru.practicum.bank.exchange.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.bank.exchange.dto.RateDto;
import ru.practicum.bank.exchange.mappers.RateMapper;
import ru.practicum.bank.exchange.repository.RateRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {
  private final RateRepository rateRepository;
  private final RateMapper rateMapper;

  @Override
  public void saveRate(RateDto rateDto) {
    var rateForUpdate = rateRepository.findByTitle(rateDto.title());
    rateForUpdate.setValue(rateDto.value());

    rateRepository.save(rateForUpdate);
  }

  @Override
  public RateDto getCurrencyRate(String currencyExchange) {
    return rateMapper.toDto(rateRepository.findByTitle(currencyExchange));
  }

  @Override
  public List<RateDto> getRates() {
    return rateMapper.toDto(rateRepository.findAll());
  }
}
