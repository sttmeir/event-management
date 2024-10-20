package com.mfortune.event.management.service.impl;

import com.mfortune.event.management.domain.Event;
import com.mfortune.event.management.domain.Visitor;
import com.mfortune.event.management.repository.EventRepository;
import com.mfortune.event.management.repository.OrganizerRepository;
import com.mfortune.event.management.repository.VisitorRepository;
import com.mfortune.event.management.service.EventService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final VisitorRepository visitorRepository;

    @Override
    public void createEvent(Event event) {
        if (event != null && event.getEventName() != null) {
            eventRepository.save(event);
        } else {
            throw new IllegalArgumentException("Event or Event Name cannot be null");
        }
    }

    @Override
    public void deleteEvent(Event event) {
        if (event != null) {
            eventRepository.delete(event);
        } else {
            throw new IllegalArgumentException("Event cannot be null");
        }
    }


    @Override
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    @Override
    public void updateEvent(Event event) {

    }

    @Override
    public void registerVisitorForEvent(Visitor visitor, Event event) {
        if (event.getVisitorList() == null) {
            event.setVisitorList(new ArrayList<>());
        }
        event.getVisitorList().add(visitor);

        if (visitor.getEventsAttended() == null) {
            visitor.setEventsAttended(new ArrayList<>());
        }
        visitor.getEventsAttended().add(event);

        visitorRepository.save(visitor);
//        eventRepository.save(event);

        System.out.println("Visitor " + visitor.getName() + " registered for event " + event.getEventName());
    }

    public List<Visitor> getVisitorsSorted(Event event) {
        return event.getVisitorList().stream()
                .sorted(Comparator.comparing(Visitor::getName))
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void testMethod() {
        List<Event> events = getEvents();
        events.forEach(event -> System.out.println("Loaded Event: " + event.getEventName()));
    }

}
