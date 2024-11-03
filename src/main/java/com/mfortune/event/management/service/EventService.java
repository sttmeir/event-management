package com.mfortune.event.management.service;

import com.mfortune.event.management.domain.Event;
import java.util.List;

public interface EventService {

    Event createEvent(String eventName, int organizerId);

    Event updateEventName(int eventId, String newEventName);

    Event addVisitorToEvent(int eventId, int visitorId);

    Event removeVisitorFromEvent(int eventId, int visitorId);

    Event findEventById(int eventId);

    List<Event> findEventsByOrganizer(int organizerId);

    void deleteEvent(int eventId);
}

