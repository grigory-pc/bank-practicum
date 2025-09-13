package ru.practicum.bank.front.ui.configs.security;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.practicum.bank.front.ui.clients.accounts.AccountsClient;
import ru.practicum.bank.front.ui.dto.UserLoginAuth;

@RequiredArgsConstructor
public class RemoteUserDetailsService implements UserDetailsService {
  private final AccountsClient accountsClient;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserLoginAuth user = accountsClient.requestGetAuthUser(username).block();

    return new User(user.login(), user.password(), Collections.singletonList(
        new SimpleGrantedAuthority("ROLE_" + user.role())));
  }
}
