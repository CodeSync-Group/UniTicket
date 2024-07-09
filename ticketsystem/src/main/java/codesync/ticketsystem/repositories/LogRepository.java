package codesync.ticketsystem.repositories;

import codesync.ticketsystem.entities.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogEntity,Long> {
}