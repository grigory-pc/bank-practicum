package ru.practicum.bank.front.ui.configs.security;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.practicum.bank.front.ui.dto.UserAuthDto;

public class UserDetailsImpl implements UserDetails {
  private final UserAuthDto user;

  public UserDetailsImpl(UserAuthDto user) {
    this.user = user;
  }

  @Override
  public String getUsername() {
    return user.login();
  }

  @Override
  public String getPassword() {
    return user.password();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(
        new SimpleGrantedAuthority("ROLE_" + user.role())
    );
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
