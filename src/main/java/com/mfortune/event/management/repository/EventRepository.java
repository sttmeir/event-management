package com.mfortune.event.management.repository;

import com.mfortune.event.management.domain.Event;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EventRepository {
    void save(Event event);
    void delete(Event event);
    List<Event> findAll();
    String findNameById(String id);
}
