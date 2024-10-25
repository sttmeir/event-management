package com.mfortune.event.management.service;

import com.mfortune.event.management.domain.Event;
import com.mfortune.event.management.domain.Organizer;
import com.mfortune.event.management.domain.Visitor;

import java.util.List;

import java.util.List;

public interface EventService {

    Event createEvent(String eventName, int organizerId);

    Event addVisitorToEvent(int eventId, int visitorId);

    Event removeVisitorFromEvent(int eventId, int visitorId);

    Event findEventById(int eventId);

    List<Event> findEventsByOrganizer(int organizerId);

    void deleteEvent(int eventId);

    Visitor addVisitor(String visitorName);

    Organizer addOrganizer(String organizerName);

    void deleteVisitor(int visitorId);
}

