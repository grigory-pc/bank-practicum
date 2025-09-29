package ru.practicum.bank.blocker.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурация Spring Security.
 */
@Slf4j
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain springSecurityFilterChain(HttpSecurity security) throws Exception {
    return  security
        .authorizeHttpRequests(requests -> requests
            .anyRequest().authenticated()
        )
        .oauth2ResourceServer(server -> server
            .jwt(Customizer.withDefaults())
        )
        .build();
  }
}