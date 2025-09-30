package ru.practicum.bank.accounts.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурация Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
    return  security
        .authorizeHttpRequests(requests -> requests
            .requestMatchers("/**").permitAll()
            .anyRequest().authenticated()
        )
        .oauth2ResourceServer(server -> server
            .jwt(Customizer.withDefaults())
        )
        .build();
  }
}