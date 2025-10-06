package ru.practicum.bank.accounts.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.practicum.bank.accounts.dto.AccountsDto;
import ru.practicum.bank.accounts.entity.Account;

/**
 * Маппер между объектами Account и AccountDto
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
  @Mapping(target = "userId", source = "user.id")
  List<AccountsDto> toDto(Iterable<Account> accounts);

  @Mapping(target = "userId", source = "user.id")
  AccountsDto toDto(Account account);

  Account toAccount(AccountsDto accountDto);
}