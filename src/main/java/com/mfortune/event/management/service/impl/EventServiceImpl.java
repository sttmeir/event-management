package com.mfortune.event.management.service.impl;

import com.mfortune.event.management.domain.Event;
import com.mfortune.event.management.domain.Organizer;
import com.mfortune.event.management.domain.Visitor;
import com.mfortune.event.management.repository.EventRepository;
import com.mfortune.event.management.repository.OrganizerRepository;
import com.mfortune.event.management.repository.VisitorRepository;
import com.mfortune.event.management.service.EventService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mfortune.event.management.constant.StringConstants.EVENT_NOT_FOUND;


@RequiredArgsConstructor
@Service
@Transactional
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final VisitorRepository visitorRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

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
                        .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND + eventId));
                event.setEventName(newEventName);
                eventRepository.save(event);
                logger.info("Updated event: {}", event);
            } catch (NumberFormatException e) {
                logger.error("Invalid eventId format in message: {}", message, e);
            }
        } else {
            logger.warn("Message format is incorrect: {}", message);
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

    @Override
    public Event updateEventName(int eventId, String newEventName) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND + eventId));
        event.setEventName(newEventName);
        return eventRepository.save(event);
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
                .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND + eventId));
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new IllegalArgumentException("Visitor not found with id: " + visitorId));

        if (event.getVisitorList() == null) {
            event.setVisitorList(new ArrayList<>());
        }

        event.getVisitorList().add(visitor);
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
                .orElseThrow(() -> new IllegalArgumentException(EVENT_NOT_FOUND + eventId));
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
            throw new IllegalArgumentException(EVENT_NOT_FOUND + eventId);
        }
    }


}
