package com.mfortune.event.management.repository;

import com.mfortune.event.management.domain.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Integer> {

}
