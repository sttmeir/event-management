package com.mfortune.event.management.service.impl;

import com.mfortune.event.management.domain.Event;
import com.mfortune.event.management.domain.Visitor;
import com.mfortune.event.management.repository.EventRepository;
import com.mfortune.event.management.repository.OrganizerRepository;
import com.mfortune.event.management.repository.VisitorRepository;
import com.mfortune.event.management.service.EventService;
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
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Event event) {
        eventRepository.delete(event);
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

}
