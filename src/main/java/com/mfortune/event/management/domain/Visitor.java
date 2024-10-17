package com.mfortune.event.management.domain;


import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
@Data
public class Visitor {
    private int id;
    private String name;
    private List<Event> eventsAttended;
}
