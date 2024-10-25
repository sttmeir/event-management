package com.mfortune.event.management.repository;

import com.mfortune.event.management.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Integer> {

}
