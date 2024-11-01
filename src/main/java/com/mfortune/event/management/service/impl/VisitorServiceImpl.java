package com.mfortune.event.management.service.impl;

import com.mfortune.event.management.domain.Visitor;
import com.mfortune.event.management.repository.VisitorRepository;
import com.mfortune.event.management.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;

    @Override
    public List<Visitor> findAll() {
        return visitorRepository.findAll();
    }

    @Override
    public Optional<Visitor> findById(int id) {
        return visitorRepository.findById(id);
    }

    /**
     * Add a new visitor to the system.
     * @param visitorName Name of the visitor.
     * @return Created Visitor.
     */
    @Override
    public Visitor addVisitor(String visitorName) {
        Visitor visitor = new Visitor();
        visitor.setName(visitorName);
        return visitorRepository.save(visitor);
    }

    /**
     * Delete a visitor from the system by their ID.
     * @param visitorId ID of the visitor.
     */
    @Override
    public void deleteVisitor(int visitorId) {
        Optional<Visitor> visitor = visitorRepository.findById(visitorId);

        if (visitor.isPresent()) {
            visitorRepository.delete(visitor.get());
        } else {
            throw new IllegalArgumentException("Visitor not found with id: " + visitorId);
        }
    }
}
