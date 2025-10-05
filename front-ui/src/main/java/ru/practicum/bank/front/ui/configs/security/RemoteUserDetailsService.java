package ru.practicum.bank.front.ui.configs.security;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.practicum.bank.front.ui.clients.accounts.AccountsClient;
import ru.practicum.bank.front.ui.dto.UserAuthDto;

@Slf4j
@RequiredArgsConstructor
public class RemoteUserDetailsService implements UserDetailsService {
  private final AccountsClient accountsClient;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserAuthDto user = accountsClient.requestGetAuthUser(username);

    log.info("Для авторизации из БД получен пользователь: {}", user);

    return new User(user.login(), user.password(), Collections.singletonList(
        new SimpleGrantedAuthority("ROLE_" + user.role())));
  }
}
