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
    @Autowired
    ConfigurationService configurationService;

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

        NotificationEntity creatorUserNotification = NotificationEntity.builder()
                .ticket(ticket)
                .user(creatorUser)
                .status(status)
                .build();

        saveNotification(creatorUserNotification);

        ConfigurationEntity creatorUserConfig = configurationService.getConfigurationById(creatorUser.getConfiguration().getId())
                .orElseThrow(() -> new EntityNotFoundException("Configuration with id " + creatorUser.getConfiguration().getId() + " does not exist."));

        if (creatorUserConfig.isMail_notifications()) {
            mailService.newTicketMail(creatorProfile.getEmail(), creatorProfile.getFirstname(), creatorProfile.getLastname(), String.valueOf(ticket.getId()));
        }
    }

    @EventListener
    public void handleTicketAnalystModifiedEventNotification(TicketAnalystModifiedEvent event) throws Exception {
        TicketEntity ticket = event.getTicket();

        UserEntity creatorUser = userService.getUserById(ticket.getCreator().getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + ticket.getCreator().getId() + " does not exist."));
        ProfileEntity creatorProfile = profileService.getProfileById(creatorUser.getProfile().getId())
                .orElseThrow(() -> new EntityNotFoundException("Profile with id " + creatorUser.getProfile().getId() + " does not exist."));

        UserEntity analystUser = userService.getUserById(ticket.getAnalyst().getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + ticket.getAnalyst().getId() + " does not exist."));
        ProfileEntity analystProfile = profileService.getProfileById(analystUser.getProfile().getId())
                .orElseThrow(() -> new EntityNotFoundException("Profile with id " + analystUser.getProfile().getId() + " does not exist."));

        StatusEntity status = ticket.getStatus();

        NotificationEntity notification = NotificationEntity.builder()
                .ticket(ticket)
                .user(creatorUser)
                .status(status)
                .build();

        NotificationEntity analystNotification = NotificationEntity.builder()
                .ticket(ticket)
                .user(analystUser)
                .status(status)
                .build();

        saveNotification(notification);
        saveNotification(analystNotification);

        ConfigurationEntity userConfig = configurationService.getConfigurationById(creatorUser.getConfiguration().getId())
                .orElseThrow(() -> new EntityNotFoundException("Configuration with id " + creatorUser.getConfiguration().getId() + " does not exist."));

        ConfigurationEntity analystConfig = configurationService.getConfigurationById(creatorUser.getConfiguration().getId())
                .orElseThrow(() -> new EntityNotFoundException("Configuration with id " + analystUser.getConfiguration().getId() + " does not exist."));


        if (userConfig.isMail_notifications()) {
            mailService.updatedTicketMail(creatorProfile.getEmail(), creatorProfile.getFirstname(), creatorProfile.getLastname(), status.getStatus(), String.valueOf(ticket.getId()));
        }

        //Here will go the method that emails the analyst to advice the new ticket that the unit assigned him
        if (analystConfig.isMail_notifications()) {
            mailService.newAnalystAssigned(analystProfile.getEmail(), analystProfile.getFirstname(), analystProfile.getLastname(), String.valueOf(ticket.getId()));
        }
    }
}
