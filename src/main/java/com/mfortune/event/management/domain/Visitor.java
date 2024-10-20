package com.mfortune.event.management.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {
    private int id;
    private String name;
    private List<Event> eventsAttended;
}
