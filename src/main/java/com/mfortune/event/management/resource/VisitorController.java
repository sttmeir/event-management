package com.mfortune.event.management.resource;

import com.mfortune.event.management.domain.Visitor;
import com.mfortune.event.management.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    @PostMapping
    public ResponseEntity<Visitor> createVisitor(@RequestBody Visitor visitor) {
        Visitor createdVisitor = visitorService.addVisitor(visitor.getName());
        return new ResponseEntity<>(createdVisitor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visitor> getVisitorById(@PathVariable int id) {
        return visitorService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Visitor>> getAllVisitors() {
        List<Visitor> visitors = visitorService.findAll();
        return ResponseEntity.ok(visitors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisitor(@PathVariable int id) {
        visitorService.deleteVisitor(id);
        return ResponseEntity.noContent().build();
    }
}
