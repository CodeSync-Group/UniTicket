package com.codesync.uniticket.services;

import com.codesync.uniticket.entities.*;
import com.codesync.uniticket.events.TicketAnalystModifiedEvent;
import com.codesync.uniticket.events.TicketCreatedEvent;
import com.codesync.uniticket.repositories.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserService userService;
    @Autowired
    ProfileService profileService;
    @Autowired
    MailService mailService;
    @Autowired
    StatusService statusService;

    public NotificationEntity saveNotification(NotificationEntity notification) {
        return notificationRepository.save(notification);
    }

    public List<NotificationEntity> getNotifications() {
        return notificationRepository.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public NotificationEntity updateNotification(NotificationEntity notification) {
        NotificationEntity existingNotification = notificationRepository.findById(notification.getId())
                .orElseThrow(() -> new EntityNotFoundException("Notification with id " + notification.getId() + " does not exist."));

        if (notification.getUser() != null) {
            existingNotification.setUser(notification.getUser());
        }

        if (notification.getStatus() != null) {
            existingNotification.setStatus(notification.getStatus());
        }

        if (notification.getTicket() != null) {
            existingNotification.setTicket(notification.getTicket());
        }

        return notificationRepository.save(existingNotification);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteNotification(Long id) throws Exception {
        try {
            notificationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @EventListener
    public void handleTicketCreatedEventNotification(TicketCreatedEvent event) throws Exception {
        TicketEntity ticket = event.getTicket();

        UserEntity creatorUser = userService.getUserById(ticket.getCreator().getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + ticket.getCreator().getId() + " does not exist."));


        ProfileEntity creatorProfile = profileService.getProfileById(creatorUser.getProfile().getId())
                .orElseThrow(() -> new EntityNotFoundException("Profile with id " + creatorUser.getProfile().getId() + " does not exist."));

        StatusEntity status = ticket.getStatus();

        NotificationEntity notification = NotificationEntity.builder()
                .ticket(ticket)
                .user(creatorUser)
                .status(status)
                .build();

        saveNotification(notification);

        mailService.newTicketMail(creatorProfile.getEmail(), creatorProfile.getFirstname(), creatorProfile.getLastname(), String.valueOf(ticket.getId()));
    }

    @EventListener
    public void handleTicketAnalystModifiedEventNotification(TicketAnalystModifiedEvent event) throws Exception {
        TicketEntity ticket = event.getTicket();

        UserEntity creatorUser = userService.getUserById(ticket.getCreator().getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + ticket.getCreator().getId() + " does not exist."));


        ProfileEntity creatorProfile = profileService.getProfileById(creatorUser.getProfile().getId())
                .orElseThrow(() -> new EntityNotFoundException("Profile with id " + creatorUser.getProfile().getId() + " does not exist."));

        StatusEntity status = ticket.getStatus();

        NotificationEntity notification = NotificationEntity.builder()
                .ticket(ticket)
                .user(creatorUser)
                .status(status)
                .build();

        saveNotification(notification);

        mailService.updatedTicketMail(creatorProfile.getEmail(), creatorProfile.getFirstname(), creatorProfile.getLastname(), status.getStatus(), String.valueOf(ticket.getId()));
    }
}
