package com.mfortune.event.management.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String eventName;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    @JsonBackReference
    @ToString.Exclude
    private Organizer organizer;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Visitor> visitorList;

    public Event(int id, String eventName) {
        this.id = id;
        this.eventName = eventName;
    }
}

