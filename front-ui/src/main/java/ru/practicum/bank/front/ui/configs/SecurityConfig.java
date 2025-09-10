package ru.practicum.bank.front.ui.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/signup").permitAll()
            .requestMatchers("/", "/main").hasRole("USER")
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .defaultSuccessUrl("/main")
            .failureHandler((request, response, exception) -> response.sendRedirect("/signup"))
            .permitAll()
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/")
        )
        .sessionManagement(session -> session
            .sessionFixation().migrateSession());

    return http.build();
  }
}
