package com.rama.reactive;

import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.rama.reactive.model.Employee;
import com.rama.reactive.repo.EmployeeRepository;

@SpringBootApplication
public class ReactiveMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveMongoApplication.class, args);
	}

	@Bean
	CommandLineRunner employees(EmployeeRepository repo) {
		return args -> {
			repo.deleteAll()
			.subscribe(null, null, () -> {
				Stream.of(new Employee(UUID.randomUUID().toString(), "Rama", 990000000000L),
						new Employee(UUID.randomUUID().toString(), "Pete", 99000L),
						new Employee(UUID.randomUUID().toString(), "Sam", 10000L))
				.forEach(emp -> {
					repo.save(emp)
					.subscribe(System.out::println);
				});
			});
		};
	}
}
