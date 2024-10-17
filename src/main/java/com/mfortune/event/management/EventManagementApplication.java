package com.mfortune.event.management;

import com.mfortune.event.management.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventManagementApplication.class, args);

		// You can test some methods from EventService class below
		// Data from the DataLoader class is loaded automatically after program execution

	}
}
