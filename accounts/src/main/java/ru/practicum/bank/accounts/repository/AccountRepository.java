package ru.practicum.bank.accounts.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.bank.accounts.entity.Account;
import ru.practicum.bank.accounts.entity.Currency;
import ru.practicum.bank.accounts.entity.User;

/**
 * Интерфейс для хранения объектов аккаунтов.
 */
public interface AccountRepository
    extends JpaRepository<Account, Long>, CrudRepository<Account, Long> {
  Optional<Account> findByCurrencyAndUser(Currency currency, User user);

  List<Account> findAllByUserId(Long userId);
}