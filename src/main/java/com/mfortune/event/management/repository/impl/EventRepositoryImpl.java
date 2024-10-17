package com.mfortune.event.management.repository.impl;

import com.mfortune.event.management.domain.Event;
import com.mfortune.event.management.repository.EventRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepositoryImpl implements EventRepository {
    private List<Event> eventList = new ArrayList<>();

    @Override
    public void save(Event event) {
        eventList.add(event);
    }

    @Override
    public void delete(Event event) {
        eventList.remove(event);
    }

    @Override
    public List<Event> findAll() {
        return eventList;
    }
}
