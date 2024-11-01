package com.mfortune.event.management.service;

import com.mfortune.event.management.domain.Visitor;

import java.util.List;
import java.util.Optional;

public interface VisitorService {
    Visitor addVisitor(String visitorName);

    void deleteVisitor(int visitorId);

    List<Visitor> findAll();

    Optional<Visitor> findById(int id);
}
