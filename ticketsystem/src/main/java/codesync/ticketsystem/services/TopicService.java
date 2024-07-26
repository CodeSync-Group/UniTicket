package codesync.ticketsystem.services;

import codesync.ticketsystem.entities.TopicEntity;
import codesync.ticketsystem.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public TopicEntity saveTopic(TopicEntity topic) {
        return topicRepository.save(topic);
    }
}
