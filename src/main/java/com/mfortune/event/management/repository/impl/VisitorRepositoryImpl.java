package com.mfortune.event.management.repository.impl;

import com.mfortune.event.management.domain.Visitor;
import com.mfortune.event.management.repository.VisitorRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VisitorRepositoryImpl implements VisitorRepository {

    private final JdbcTemplate jdbcTemplate;

    public VisitorRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Visitor visitor) {
        String sql = "INSERT INTO visitors (name) VALUES (?)";
        jdbcTemplate.update(sql, visitor.getName());
    }

    @Override
    public void delete(Visitor visitor) {
        String sql = "DELETE FROM visitors WHERE id = ?";
        jdbcTemplate.update(sql, visitor.getId());
    }

    @Override
    public List<Visitor> findAll() {
        String sql = "SELECT * FROM visitors";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Visitor visitor = new Visitor();
            visitor.setId(rs.getInt("id"));
            visitor.setName(rs.getString("name"));
            return visitor;
        });
    }
}
