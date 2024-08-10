package com.codesync.uniticket.services;

import com.codesync.uniticket.entities.TopicEntity;
import com.codesync.uniticket.repositories.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    public TopicEntity saveTopic(TopicEntity topic) {
        return topicRepository.save(topic);
    }

    public List<TopicEntity> getTopics() { return topicRepository.findAll(); }

    @PreAuthorize("hasAuthority('ADMIN')")
    public TopicEntity updateTopic(TopicEntity topic) {
        TopicEntity existingTopic = topicRepository.findById(topic.getId())
                .orElseThrow(() -> new EntityNotFoundException("Topic with id " + topic.getId() + " does not exist."));

        if (topic.getTopic() != null){
            existingTopic.setTopic(topic.getTopic());
        }

        return topicRepository.save(existingTopic);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteTopic(Long id) throws Exception {
        try {
            topicRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
