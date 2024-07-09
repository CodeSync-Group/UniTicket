package codesync.ticketsystem.repositories;

import codesync.ticketsystem.entities.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity,Long> {
}
