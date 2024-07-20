package ru.ibash.quotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ibash.quotes.service.QuotService;

@SpringBootApplication
public class QuotesApplication implements CommandLineRunner {
	@Autowired
	QuotService service;

	public static void main(String[] args) {
		SpringApplication.run(QuotesApplication.class, args);
	}

	@Override
	public void run(String... args)  {
//		var index = service.getIndex();
//		System.out.println(index);
	}
}
