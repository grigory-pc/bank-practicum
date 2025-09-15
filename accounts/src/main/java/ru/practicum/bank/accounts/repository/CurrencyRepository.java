package ru.practicum.bank.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.bank.accounts.entity.Currency;

/**
 * Интерфейс для хранения объектов курсов валют.
 */
public interface CurrencyRepository extends JpaRepository<Currency, Long>, CrudRepository<Currency, Long> {
}