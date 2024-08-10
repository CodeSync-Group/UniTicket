package com.codesync.uniticket.services;

import com.codesync.uniticket.entities.LogEntity;
import com.codesync.uniticket.entities.TicketEntity;
import com.codesync.uniticket.entities.UserEntity;
import com.codesync.uniticket.events.TicketAnalystModifiedEvent;
import com.codesync.uniticket.events.TicketCreatedEvent;
import com.codesync.uniticket.repositories.LogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {
    @Autowired
    LogRepository logRepository;
    @Autowired
    UserService userService;

    public LogEntity saveLog(LogEntity log) { return logRepository.save(log); }

    public List<LogEntity> getLogs() { return logRepository.findAll(); }

    @PreAuthorize("hasAuthority('ADMIN')")
    public LogEntity updateLog(LogEntity log) {
        LogEntity existingLog = logRepository.findById(log.getId())
                .orElseThrow(() -> new EntityNotFoundException("Log with id " + log.getId() + " does not exist."));

        if (log.getDateTime() != null) {
            existingLog.setDateTime(log.getDateTime());
        }

        if (log.getAnnotation() != null) {
            existingLog.setAnnotation(log.getAnnotation());
        }

        return logRepository.save(existingLog);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteLog(Long id) throws Exception {
        try {
            logRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @EventListener
    public void handleTicketCreatedEventLog(TicketCreatedEvent event) {
        TicketEntity ticket = event.getTicket();

        UserEntity creatorUser = userService.getUserById(ticket.getCreator().getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + ticket.getCreator().getId() + " does not exist."));

        LogEntity log = logBuilder("Created Ticket", ticket, creatorUser);

        saveLog(log);
    }

    @EventListener
    public void handleTicketAnalystModifiedEventLog(TicketAnalystModifiedEvent event) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentUsername = authentication.getName();

        UserEntity creatorUser = userService.getUserByUsername(currentUsername)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + currentUsername + " does not exist."));

        TicketEntity ticket = event.getTicket();

        LogEntity log = logBuilder("Assigned Analyst", ticket, creatorUser);

        saveLog(log);
    }

    private LogEntity logBuilder(String annotation, TicketEntity ticket, UserEntity creatorUser) {
        return LogEntity.builder()
                .dateTime(LocalDateTime.now())
                .role(creatorUser.getRole())
                .annotation(annotation)
                .creator(ticket.getCreator())
                .ticket(ticket)
                .headship(ticket.getHeadship())
                .analyst(ticket.getAnalyst())
                .unit(ticket.getUnit())
                .category(ticket.getCategory())
                .status(ticket.getStatus())
                .build();
    }
}
