package ru.practicum.bank.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.bank.accounts.entity.User;

/**
 * Интерфейс для хранения объектов пользователей.
 */
public interface UserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long> {

  @Query(value = """
      SELECT id, login, password, name, birthdate, role 
      FROM users 
      WHERE login = :login
      """,
         nativeQuery = true)
  User findByLogin(String login);
}