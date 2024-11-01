package com.mfortune.event.management.service.impl;

import com.mfortune.event.management.domain.Organizer;
import com.mfortune.event.management.repository.OrganizerRepository;
import com.mfortune.event.management.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository organizerRepository;
    /**
     * Create a new organizer in the system.
     * @param organizerName Name of the organizer.
     * @return Created Organizer.
     */
    @Override
    public Organizer addOrganizer(String organizerName) {
        Organizer organizer = new Organizer();
        organizer.setName(organizerName);
        return organizerRepository.save(organizer);
    }

    @Override
    public Optional<Organizer> findById(int organizerId) {
        return organizerRepository.findById(organizerId);
    }

    @Override
    public List<Organizer> findAll() {
        return organizerRepository.findAll();
    }

    @Override
    public void deleteOrganizer(int organizerId) {
        organizerRepository.deleteById(organizerId);
    }
}
