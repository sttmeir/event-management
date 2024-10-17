package com.mfortune.event.management.repository;

import com.mfortune.event.management.domain.Event;

import java.util.List;

public interface EventRepository {
    void save(Event event);
    void delete(Event event);
    List<Event> findAll();
}
