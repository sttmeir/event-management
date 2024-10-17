package com.mfortune.event.management.domain;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
@Data
public class Event {
    private int id;
    private String eventName;
    private Organizer organizer;
    private List<Visitor> visitorList;
}
