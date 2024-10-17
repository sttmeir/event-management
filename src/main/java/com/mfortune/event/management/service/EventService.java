package com.mfortune.event.management.service;

import com.mfortune.event.management.domain.Event;
import com.mfortune.event.management.domain.Visitor;

import java.util.List;

public interface EventService {
    void createEvent(Event event);
    void deleteEvent(Event event);
    void updateEvent(Event event);
    List<Event> getEvents();
    void registerVisitorForEvent(Visitor visitor, Event event);
    List<Visitor> getVisitorsSorted(Event event);
}
