package com.mfortune.event.management.resource;

import com.mfortune.event.management.domain.Event;
import com.mfortune.event.management.domain.Organizer;
import com.mfortune.event.management.service.EventService;
import com.mfortune.event.management.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final OrganizerService organizerService;

    // Create a new event
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestParam String eventName, @RequestParam int organizerId) {
        Organizer organizer = organizerService.findById(organizerId)
                .orElseThrow(() -> new IllegalArgumentException("Organizer not found with id: " + organizerId));
        Event event = eventService.createEvent(eventName, organizer.getId());
        return ResponseEntity.ok(event);
    }

    // Get event by ID
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable int eventId) {
        Event event = eventService.findEventById(eventId);
        return ResponseEntity.ok(event);
    }

    // Get all events for a specific organizer
    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<Event>> getEventsByOrganizer(@PathVariable int organizerId) {
        List<Event> events = eventService.findEventsByOrganizer(organizerId);
        return ResponseEntity.ok(events);
    }

    // Update event name
    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEventName(@PathVariable int eventId, @RequestParam String newEventName) {
        Event updatedEvent = eventService.updateEventName(eventId, newEventName);
        return ResponseEntity.ok(updatedEvent);
    }

    // Delete an event
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }

    // Add a visitor to an event
    @PostMapping("/{eventId}/visitors/{visitorId}")
    public ResponseEntity<Event> addVisitorToEvent(@PathVariable int eventId, @PathVariable int visitorId) {
        Event updatedEvent = eventService.addVisitorToEvent(eventId, visitorId);
        return ResponseEntity.ok(updatedEvent);
    }

    // Remove a visitor from an event
    @DeleteMapping("/{eventId}/visitors/{visitorId}")
    public ResponseEntity<Event> removeVisitorFromEvent(@PathVariable int eventId, @PathVariable int visitorId) {
        Event updatedEvent = eventService.removeVisitorFromEvent(eventId, visitorId);
        return ResponseEntity.ok(updatedEvent);
    }
}
