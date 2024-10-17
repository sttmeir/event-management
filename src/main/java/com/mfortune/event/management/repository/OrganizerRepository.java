package com.mfortune.event.management.repository;

import com.mfortune.event.management.domain.Organizer;

import java.util.List;

public interface OrganizerRepository {
    void save(Organizer organizer);
    void delete(Organizer organizer);
    List<Organizer> findAll();
}
