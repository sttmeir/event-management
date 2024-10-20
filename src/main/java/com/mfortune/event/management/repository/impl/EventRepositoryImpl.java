package com.mfortune.event.management.repository.impl;

import com.mfortune.event.management.domain.Event;
import com.mfortune.event.management.repository.EventRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Event event) {
        String sql = "INSERT INTO events (event_name, organizer_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, event.getEventName(), event.getOrganizer().getId());
    }

    @Override
    public void delete(Event event) {
        String sql = "DELETE FROM events WHERE id = ?";
        jdbcTemplate.update(sql, event.getId());
    }

    @Override
    public List<Event> findAll() {
        String sql = "SELECT * FROM events";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Event event = new Event();
            event.setId(rs.getInt("id"));
            event.setEventName(rs.getString("event_name"));
            return event;
        });
    }

    @Override
    public String findNameById(String id) {
        return "";
    }
}
