package codesync.ticketsystem.controllers;

import codesync.ticketsystem.entities.NotificationEntity;
import codesync.ticketsystem.services.NotificationService;
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
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @Operation(summary = "Get notifications")
    @GetMapping("/")
    public ResponseEntity<List<NotificationEntity>> getNotifications() {
        List<NotificationEntity> notifications = notificationService.getNotifications();
        return ResponseEntity.ok(notifications);
    }
}
