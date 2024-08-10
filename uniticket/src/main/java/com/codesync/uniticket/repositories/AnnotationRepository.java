package com.codesync.uniticket.repositories;

import com.codesync.uniticket.entities.AnnotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnotationRepository extends JpaRepository<AnnotationEntity, Long> {

    Optional<List<AnnotationEntity>> findByTicketId(Long ticketId);
}
