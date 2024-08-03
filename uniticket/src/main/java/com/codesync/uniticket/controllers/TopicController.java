package com.codesync.uniticket.controllers;

import com.codesync.uniticket.entities.TopicEntity;
import com.codesync.uniticket.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/topics")
public class TopicController {
    @Autowired
    TopicService topicService;

    @Operation(summary = "Get topics")
    @GetMapping("/")
    public ResponseEntity<List<TopicEntity>> getTopics() {
        List<TopicEntity> topics = topicService.getTopics();
        return ResponseEntity.ok(topics);
    }
}
