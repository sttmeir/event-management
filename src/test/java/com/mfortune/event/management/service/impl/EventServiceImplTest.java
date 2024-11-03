package com.mfortune.event.management.service.impl;

import com.mfortune.event.management.domain.Event;
import com.mfortune.event.management.domain.Organizer;
import com.mfortune.event.management.domain.Visitor;
import com.mfortune.event.management.repository.EventRepository;
import com.mfortune.event.management.repository.OrganizerRepository;
import com.mfortune.event.management.repository.VisitorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EventServiceImplTest {

    @Mock
    EventRepository eventRepository;

    @Mock
    OrganizerRepository organizerRepository;

    @Mock
    VisitorRepository visitorRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    EventServiceImpl eventServiceImpl;

    @InjectMocks
    OrganizerServiceImpl organizerServiceImpl;

    @InjectMocks
    VisitorServiceImpl visitorServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessage() {
        // Given
        String topic = "event-updates";
        String message = "123:New Event Name";

        // When
        eventServiceImpl.sendMessage(topic, message);

        // Then
        verify(kafkaTemplate).send(topic, message);
    }

    @Test
    void testListenWithCorrectMessageFormat() {
        String message = "1:New Event Name";
        when(eventRepository.findById(1)).thenReturn(Optional.of(new Event(1, "Old Event Name")));

        eventServiceImpl.listen(message);

        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void testCreateEvent() {
        Organizer mockOrganizer = new Organizer(0, "OrganizerName", new ArrayList<>());
        when(organizerRepository.findById(0)).thenReturn(Optional.of(mockOrganizer));

        Event mockEvent = new Event(0, "eventName", mockOrganizer, new ArrayList<>());
        when(eventRepository.save(any(Event.class))).thenReturn(mockEvent);

        Event result = eventServiceImpl.createEvent("eventName", 0);

        assertEquals(mockEvent, result);
    }

    @Test
    void testCreateEventWithNonExistentOrganizer() {
        when(organizerRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> eventServiceImpl.createEvent("New Event", 1)
        );
        assertEquals("Organizer not found with id: 1", exception.getMessage());
    }


    @Test
    void testAddVisitorToEvent() {
        Event mockEvent = new Event(0, "eventName", new Organizer(1, "OrganizerName", null), null);
        Visitor mockVisitor = new Visitor(0, "VisitorName", mockEvent);

        when(eventRepository.findById(0)).thenReturn(Optional.of(mockEvent));
        when(visitorRepository.findById(0)).thenReturn(Optional.of(mockVisitor));
        when(eventRepository.save(any(Event.class))).thenReturn(mockEvent);

        Event result = eventServiceImpl.addVisitorToEvent(0, 0);

        assertEquals(0, result.getId());
        assertEquals("eventName", result.getEventName());
        assertEquals("OrganizerName", result.getOrganizer().getName());
        assertEquals("VisitorName", result.getVisitorList().get(0).getName());
    }

    @Test
    void testAddVisitorToEventWithNonExistentEvent() {
        when(eventRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> eventServiceImpl.addVisitorToEvent(1, 1)
        );
        assertEquals("Event not found with id: 1", exception.getMessage());
    }

    @Test
    void testRemoveVisitorFromEvent() {
        Organizer mockOrganizer = new Organizer(1, "OrganizerName", new ArrayList<>());
        Event mockEvent = new Event(1, "eventName", mockOrganizer, new ArrayList<>());
        Visitor mockVisitor = new Visitor(1, "VisitorName", mockEvent);
        mockEvent.getVisitorList().add(mockVisitor);

        when(eventRepository.findById(1)).thenReturn(Optional.of(mockEvent));
        when(visitorRepository.findById(1)).thenReturn(Optional.of(mockVisitor));
        when(eventRepository.save(any(Event.class))).thenReturn(mockEvent);


        Event result = eventServiceImpl.removeVisitorFromEvent(1, 1);

        // Assertions
        Assertions.assertTrue(result.getVisitorList().isEmpty(), "Visitor list should be empty after removal");
        verify(visitorRepository).save(mockVisitor);
        verify(eventRepository).save(mockEvent);
    }

    @Test
    void testRemoveVisitorFromEventWithNonExistentEvent() {
        when(eventRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> eventServiceImpl.removeVisitorFromEvent(1, 1)
        );
        assertEquals("Event or Visitor not found with given IDs.", exception.getMessage());
    }

    @Test
    void testFindEventById_ThrowsExceptionWhenNotFound() {
        when(eventRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventServiceImpl.findEventById(0);
        });

        assertEquals("Event not found with id: 0", exception.getMessage());
    }


    @Test
    void testFindEventsByOrganizer() {
        Organizer mockOrganizer = new Organizer(0, "OrganizerName", new ArrayList<>());
        Event mockEvent = new Event(0, "eventName", mockOrganizer, new ArrayList<>());
        mockOrganizer.getEvents().add(mockEvent);

        when(organizerRepository.findById(0)).thenReturn(Optional.of(mockOrganizer));

        List<Event> result = eventServiceImpl.findEventsByOrganizer(0);

        assertEquals(1, result.size());
        assertEquals(mockEvent, result.get(0));
    }

    @Test
    void testDeleteEvent() {
        Event mockEvent = new Event(0, "eventName", new Organizer(1, "OrganizerName", null), new ArrayList<>());
        when(eventRepository.findById(0)).thenReturn(Optional.of(mockEvent));

        eventServiceImpl.deleteEvent(0);

        verify(eventRepository).delete(mockEvent);

    }

    @Test
    void testDeleteEventWithNonExistentId() {
        when(eventRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> eventServiceImpl.deleteEvent(1)
        );
        assertEquals("Event not found with id: 1", exception.getMessage());
    }

    @Test
    void testAddVisitor() {
        Visitor mockVisitor = new Visitor(0, "visitorName", null);
        when(visitorRepository.save(any(Visitor.class))).thenReturn(mockVisitor);

        Visitor result = visitorServiceImpl.addVisitor("visitorName");

        assertEquals(mockVisitor, result);
    }

    @Test
    void testAddOrganizer() {
        Organizer mockOrganizer = new Organizer(0, "organizerName", new ArrayList<>());
        when(organizerRepository.save(any(Organizer.class))).thenReturn(mockOrganizer);

        Organizer result = organizerServiceImpl.addOrganizer("organizerName");

        assertEquals(mockOrganizer, result);
    }

    @Test
    void testDeleteVisitor() {
        Visitor mockVisitor = new Visitor(0, "VisitorName", new Event(1, "eventName", new Organizer(1, "OrganizerName", null), new ArrayList<>()));
        when(visitorRepository.findById(0)).thenReturn(Optional.of(mockVisitor));

        visitorServiceImpl.deleteVisitor(0);

        verify(visitorRepository).delete(mockVisitor);
    }


}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme