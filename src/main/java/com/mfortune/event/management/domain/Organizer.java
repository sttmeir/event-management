package com.mfortune.event.management.domain;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
@Data
public class Organizer {
    private int id;
    private String name;
    private List<Event> events;
}
