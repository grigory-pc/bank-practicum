package ru.practicum.bank.accounts.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.practicum.bank.accounts.dto.UserAuthDto;
import ru.practicum.bank.accounts.dto.UserDto;
import ru.practicum.bank.accounts.dto.UserFullDto;
import ru.practicum.bank.accounts.dto.UserShortDto;
import ru.practicum.bank.accounts.entity.User;

/**
 * Маппер между объектами User и UserDto
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  @Mapping(target = "role", constant = "USER")
  User toUser(UserDto dto);

  UserAuthDto toAuthDto(User user);

  UserFullDto toFullDto(User user);

  List<UserShortDto> toShortDto(Iterable<User> users);
}