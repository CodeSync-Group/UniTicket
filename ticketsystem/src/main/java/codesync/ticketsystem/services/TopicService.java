package codesync.ticketsystem.services;

import codesync.ticketsystem.entities.TopicEntity;
import codesync.ticketsystem.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    public TopicEntity saveTopic(TopicEntity topic) {
        return topicRepository.save(topic);
    }


}
