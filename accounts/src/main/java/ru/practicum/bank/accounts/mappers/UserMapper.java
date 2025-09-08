package ru.practicum.bank.accounts.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.bank.accounts.dto.UserDto;
import ru.practicum.bank.accounts.entity.User;

/**
 * Маппер между объектами User и UserDto
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(target = "role", defaultExpression = "java(\"USER\")")
  User toUser(UserDto dto);

  UserDto toDto(User user);
}