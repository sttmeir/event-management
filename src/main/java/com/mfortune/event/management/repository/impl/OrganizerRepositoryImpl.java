package com.mfortune.event.management.repository.impl;

import com.mfortune.event.management.domain.Organizer;
import com.mfortune.event.management.repository.OrganizerRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrganizerRepositoryImpl implements OrganizerRepository {

    private List<Organizer> organizers = new ArrayList<>();

    @Override
    public void save(Organizer organizer) {
        organizers.add(organizer);
    }

    @Override
    public void delete(Organizer organizer) {
        organizers.remove(organizer);
    }

    @Override
    public List<Organizer> findAll() {
        return organizers;
    }
}
