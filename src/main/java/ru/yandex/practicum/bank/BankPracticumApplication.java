package ru.yandex.practicum.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BankPracticumApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankPracticumApplication.class, args);
	}
}