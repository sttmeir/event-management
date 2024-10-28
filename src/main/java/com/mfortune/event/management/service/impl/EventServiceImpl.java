package com.mfortune.event.management.service.impl;

import com.mfortune.event.management.domain.Event;
import com.mfortune.event.management.domain.Organizer;
import com.mfortune.event.management.domain.Visitor;
import com.mfortune.event.management.repository.EventRepository;
import com.mfortune.event.management.repository.OrganizerRepository;
import com.mfortune.event.management.repository.VisitorRepository;
import com.mfortune.event.management.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final VisitorRepository visitorRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    // Event Updates via Messaging
    // messages in the format: (e.g., "123:New Event Name")
    @KafkaListener(topics = "event-updates", groupId = "event-management-service-group")
    public void listen(String message) {
        String[] parts = message.split(":");

        if (parts.length == 2) {
            try {
                int eventId = Integer.parseInt(parts[0].trim());
                String newEventName = parts[1].trim();

                Event event = eventRepository.findById(eventId)
                        .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + eventId));
                event.setEventName(newEventName);
                eventRepository.save(event);
                System.out.println("Updated event: " + event);
            } catch (NumberFormatException e) {
                System.err.println("Invalid eventId format in message: " + message);
            }
        } else {
            System.err.println("Message format is incorrect: " + message);
        }
    }

    public Event createEvent(String eventName, int organizerId) {
        Optional<Organizer> organizer = organizerRepository.findById(organizerId);
        
        if (organizer.isPresent()) {
            Event newEvent = new Event();
            newEvent.setEventName(eventName);
            newEvent.setOrganizer(organizer.get());
            return eventRepository.save(newEvent);
        } else {
            throw new IllegalArgumentException("Organizer not found with id: " + organizerId);
        }
    }

    /**
     * Add a visitor to an existing event.
     * @param eventId ID of the event.
     * @param visitorId ID of the visitor.
     * @return Updated Event with the new visitor.
     */
    @Transactional(rollbackFor = Exception.class)
    public Event addVisitorToEvent(int eventId, int visitorId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + eventId));
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new IllegalArgumentException("Visitor not found with id: " + visitorId));

        if (event.getVisitorList() == null) {
            event.setVisitorList(new ArrayList<>()); // Initialize visitor list if null
        }

        event.getVisitorList().add(visitor); // Add visitor to the list
        return eventRepository.save(event);
    }

    /**
     * Remove a visitor from an event.
     * @param eventId ID of the event.
     * @param visitorId ID of the visitor.
     * @return Updated Event after the visitor is removed.
     */
    public Event removeVisitorFromEvent(int eventId, int visitorId) {
        Optional<Event> event = eventRepository.findById(eventId);
        Optional<Visitor> visitor = visitorRepository.findById(visitorId);

        if (event.isPresent() && visitor.isPresent()) {
            Event eventEntity = event.get();
            Visitor visitorEntity = visitor.get();

            eventEntity.getVisitorList().remove(visitorEntity);
            visitorEntity.setEvent(null);

            visitorRepository.save(visitorEntity);
            return eventRepository.save(eventEntity);
        } else {
            throw new IllegalArgumentException("Event or Visitor not found with given IDs.");
        }
    }

    /**
     * Find an event by its ID.
     * @param eventId ID of the event.
     * @return Event found by its ID.
     */
    @Transactional(readOnly = true)
    public Event findEventById(int eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + eventId));
    }

    /**
     * List all events for a specific organizer.
     * @param organizerId ID of the organizer.
     * @return List of Events.
     */
    @Transactional(readOnly = true)
    public List<Event> findEventsByOrganizer(int organizerId) {
        Optional<Organizer> organizer = organizerRepository.findById(organizerId);

        if (organizer.isPresent()) {
            return organizer.get().getEvents();
        } else {
            throw new IllegalArgumentException("Organizer not found with id: " + organizerId);
        }
    }

    /**
     * Delete an event by its ID.
     * @param eventId ID of the event.
     */
    public void deleteEvent(int eventId) {
        Optional<Event> event = eventRepository.findById(eventId);

        if (event.isPresent()) {
            eventRepository.delete(event.get());
        } else {
            throw new IllegalArgumentException("Event not found with id: " + eventId);
        }
    }

    /**
     * Add a new visitor to the system.
     * @param visitorName Name of the visitor.
     * @return Created Visitor.
     */
    public Visitor addVisitor(String visitorName) {
        Visitor visitor = new Visitor();
        visitor.setName(visitorName);
        return visitorRepository.save(visitor);
    }

    /**
     * Create a new organizer in the system.
     * @param organizerName Name of the organizer.
     * @return Created Organizer.
     */
    public Organizer addOrganizer(String organizerName) {
        Organizer organizer = new Organizer();
        organizer.setName(organizerName);
        return organizerRepository.save(organizer);
    }

    /**
     * Delete a visitor from the system by their ID.
     * @param visitorId ID of the visitor.
     */
    public void deleteVisitor(int visitorId) {
        Optional<Visitor> visitor = visitorRepository.findById(visitorId);

        if (visitor.isPresent()) {
            visitorRepository.delete(visitor.get());
        } else {
            throw new IllegalArgumentException("Visitor not found with id: " + visitorId);
        }
    }

}
