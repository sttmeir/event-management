package com.mfortune.event.management.repository;

import com.mfortune.event.management.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Integer> {

}
