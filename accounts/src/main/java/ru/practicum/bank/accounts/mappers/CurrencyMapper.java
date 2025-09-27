package ru.practicum.bank.accounts.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practicum.bank.accounts.dto.CurrencyDto;
import ru.practicum.bank.accounts.entity.Currency;

/**
 * Маппер между объектами Currency и CurrencyDto
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CurrencyMapper {
  List<CurrencyDto> toDto(Iterable<Currency> currencies);

  CurrencyDto toDto(Currency currency);
  Currency toCurrency(CurrencyDto currencyDto);
}