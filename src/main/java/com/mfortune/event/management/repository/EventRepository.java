package com.mfortune.event.management.repository;

import com.mfortune.event.management.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

}
