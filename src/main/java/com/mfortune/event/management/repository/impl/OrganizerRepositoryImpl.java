package com.mfortune.event.management.repository.impl;

import com.mfortune.event.management.domain.Organizer;
import com.mfortune.event.management.repository.OrganizerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrganizerRepositoryImpl implements OrganizerRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrganizerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Organizer organizer) {
        String sql = "INSERT INTO organizers (name) VALUES (?)";
        jdbcTemplate.update(sql, organizer.getName());
    }

    @Override
    public void delete(Organizer organizer) {
        String sql = "DELETE FROM organizers WHERE id = ?";
        jdbcTemplate.update(sql, organizer.getId());
    }

    @Override
    public List<Organizer> findAll() {
        String sql = "SELECT * FROM organizers";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Organizer organizer = new Organizer();
            organizer.setId(rs.getInt("id"));
            organizer.setName(rs.getString("name"));
            return organizer;
        });
    }
}
