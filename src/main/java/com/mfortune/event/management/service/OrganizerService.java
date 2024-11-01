package com.mfortune.event.management.service;

import com.mfortune.event.management.domain.Organizer;

import java.util.List;
import java.util.Optional;

public interface OrganizerService {
    Organizer addOrganizer(String organizerName);
    Optional<Organizer> findById(int organizerId);
    List<Organizer> findAll();
    void deleteOrganizer(int organizerId);
}
