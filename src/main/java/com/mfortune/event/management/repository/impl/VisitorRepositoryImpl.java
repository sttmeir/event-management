package com.mfortune.event.management.repository.impl;

import com.mfortune.event.management.domain.Visitor;
import com.mfortune.event.management.repository.VisitorRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VisitorRepositoryImpl implements VisitorRepository {
    private List<Visitor> visitors = new ArrayList<>();

    @Override
    public void save(Visitor visitor) {
        visitors.add(visitor);
    }

    @Override
    public void delete(Visitor visitor) {
        visitors.remove(visitor);
    }

    @Override
    public List<Visitor> findAll() {
        return visitors;
    }
}
