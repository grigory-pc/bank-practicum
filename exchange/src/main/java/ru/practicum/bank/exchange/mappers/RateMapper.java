package ru.practicum.bank.exchange.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practicum.bank.exchange.dto.RateDto;
import ru.practicum.bank.exchange.entity.Rate;

/**
 * Маппер между объектами Rate и RateDto
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RateMapper {
  RateDto toDto(Rate rate);

  List<RateDto> toDto(Iterable<Rate> rates);
}