package com.mfortune.event.management.service;

import com.mfortune.event.management.domain.Event;
import com.mfortune.event.management.domain.Organizer;
import com.mfortune.event.management.domain.Visitor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    // This is the data loader class that automatically add initial data to lists.
    // You can use this class to test some methods from service class.

    private final EventService eventService;

    @Override
    public void run(String... args) throws Exception {
        Organizer organizer = Organizer.builder()
                .id(1)
                .name("Jason Parser")
                .build();

        Event event1 = Event.builder()
                .id(1)
                .eventName("Spring Boot Conference")
                .organizer(organizer)
                .build();

        Event event2 = Event.builder()
                .id(2)
                .eventName("Java Summit")
                .organizer(organizer)
                .build();

        Visitor visitor1 = Visitor.builder()
                .id(1)
                .name("Meirzhan Sattibayev")
                .build();

        Visitor visitor2 = Visitor.builder()
                .id(2)
                .name("Just a regular user")
                .build();

        Visitor visitor3 = Visitor.builder()
                .id(3)
                .name("Test User")
                .build();

        Visitor visitor4 = Visitor.builder()
                .id(4)
                .name("John Doe")
                .build();

        Visitor visitor5 = Visitor.builder()
                .id(5)
                .name("Ryan Gosling")
                .build();

        eventService.createEvent(event1);
        eventService.createEvent(event2);

        eventService.registerVisitorForEvent(visitor1, event1);
        eventService.registerVisitorForEvent(visitor2, event1);
        eventService.registerVisitorForEvent(visitor3, event1);
        eventService.registerVisitorForEvent(visitor4, event1);
        eventService.registerVisitorForEvent(visitor5, event1);
        eventService.registerVisitorForEvent(visitor2, event2);

        eventService.getEvents().forEach(e -> System.out.println("Event: " + e.getEventName()));

        List<Visitor> sorted = eventService.getVisitorsSorted(event1);


        System.out.println("Sorted list of visitors of an event: " + event1.getEventName());
        sorted.stream()
                .map(Visitor::getName)
                .forEach(System.out::println);
    }
}
