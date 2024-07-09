package codesync.ticketsystem.repositories;

import codesync.ticketsystem.entities.StatusEntity;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
}
