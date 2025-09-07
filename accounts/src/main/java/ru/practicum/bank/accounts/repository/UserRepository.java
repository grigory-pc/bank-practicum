package ru.practicum.bank.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.bank.accounts.entity.User;

/**
 * Интерфейс для хранения объектов пользователей.
 */
public interface UserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long> {
}