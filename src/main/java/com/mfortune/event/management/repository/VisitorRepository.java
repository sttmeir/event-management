package com.mfortune.event.management.repository;

import com.mfortune.event.management.domain.Visitor;

import java.util.List;

public interface VisitorRepository {
    void save(Visitor visitor);
    void delete(Visitor visitor);
    List<Visitor> findAll();
}
