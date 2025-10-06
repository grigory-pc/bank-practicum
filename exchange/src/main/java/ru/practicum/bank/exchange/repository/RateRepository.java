package ru.practicum.bank.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.bank.exchange.entity.Rate;

/**
 * Интерфейс для хранения объектов курса валюты.
 */
public interface RateRepository extends JpaRepository<Rate, Long>, CrudRepository<Rate, Long> {
  Rate findByTitle(String rateTitle);
}
