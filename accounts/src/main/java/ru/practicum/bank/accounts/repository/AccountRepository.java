package ru.practicum.bank.accounts.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.bank.accounts.entity.Account;
import ru.practicum.bank.accounts.entity.User;

/**
 * Интерфейс для хранения объектов аккаунтов.
 */
public interface AccountRepository
    extends JpaRepository<Account, Long>, CrudRepository<Account, Long> {
  List<Account> findAllByUser(User user);
}