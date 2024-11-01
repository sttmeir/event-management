package com.mfortune.event.management.resource;

import com.mfortune.event.management.domain.Organizer;
import com.mfortune.event.management.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizers")
@RequiredArgsConstructor
public class OrganizerController {

    private final OrganizerService organizerService;

    @PostMapping
    public ResponseEntity<Organizer> createOrganizer(@RequestBody Organizer organizer) {
        Organizer createdOrganizer = organizerService.addOrganizer(organizer.getName());
        return new ResponseEntity<>(createdOrganizer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organizer> getOrganizerById(@PathVariable int id) {
        return organizerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Organizer>> getAllOrganizers() {
        List<Organizer> organizers = organizerService.findAll();
        return ResponseEntity.ok(organizers);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizer(@PathVariable int id) {
        organizerService.deleteOrganizer(id);
        return ResponseEntity.noContent().build();
    }

}
