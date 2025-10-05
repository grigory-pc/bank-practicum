package ru.practicum.bank.exchange.configs;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

/**
 * Конфигурация dataSource.
 */
@Component
@PropertySource(value = "classpath:application.yml")
@RequiredArgsConstructor
public class DataSourceConfig {
  private final Environment environment;

  @Bean
  public DataSource getDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driverClassName"));
    dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
    dataSource.setUsername(environment.getRequiredProperty("spring.datasource.username"));
    dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));

    return dataSource;
  }

  /**
   * После инициализации контекста создание таблиц в БД.
   * "V1__init_scheme.sql" - Файл должен находиться в ресурсах
   *
   * @param event - событие после инициализации контекста.
   */
  @EventListener
  public void populate(ContextRefreshedEvent event) {
    DataSource dataSource = event.getApplicationContext().getBean(DataSource.class);

    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(new ClassPathResource("V1__init_scheme.sql"));
    populator.addScript(new ClassPathResource("V1__init_data.sql"));
    populator.execute(dataSource);
  }
}