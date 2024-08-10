package com.codesync.uniticket.services;

import com.codesync.uniticket.entities.StatusEntity;
import com.codesync.uniticket.entities.TicketEntity;
import com.codesync.uniticket.events.TicketAnalystModifiedEvent;
import com.codesync.uniticket.events.TicketCreatedEvent;
import com.codesync.uniticket.repositories.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    UserService userService;
    @Autowired
    StatusService statusService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Value("1")
    private Long NEW_TICKET_STATUS_ID;

    @Value("2")
    private Long NEW_ANALYST_STATUS_ID;

    public TicketEntity saveTicket(TicketEntity ticket) {
        TicketEntity savedTicket = ticketRepository.save(ticket);

        StatusEntity status = statusService.getStatusById(NEW_TICKET_STATUS_ID)
                .orElseThrow(() -> new EntityNotFoundException("Status with id " + NEW_TICKET_STATUS_ID + " does not exist."));

        savedTicket.setStatus(status);

        eventPublisher.publishEvent(new TicketCreatedEvent(this, savedTicket));
        return savedTicket;
    }

    public List<TicketEntity> getTickets() { return ticketRepository.findAll(); }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HEADUNIT', 'HEADSHIP', 'ANALYST')")
    public TicketEntity updateTicket(TicketEntity ticket) throws Exception {
        TicketEntity existingTicket = ticketRepository.findById(ticket.getId())
                .orElseThrow(() -> new EntityNotFoundException("Ticket with id " + ticket.getId() + " does not exist."));

        if (ticket.getAnalyst() != null) {
            existingTicket = updateTicketAnalyst(existingTicket, ticket);
        }
        return existingTicket;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteTicket(Long id) throws Exception {
        try {
            ticketRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HEADUNIT', 'HEADSHIP')")
    private TicketEntity updateTicketAnalyst(TicketEntity existingTicket, TicketEntity ticket) throws Exception {
        if (!Objects.equals(existingTicket.getAnalyst().getId(), ticket.getAnalyst().getId())) {
            StatusEntity status = statusService.getStatusById(NEW_ANALYST_STATUS_ID)
                    .orElseThrow(() -> new EntityNotFoundException("Status with id " + NEW_ANALYST_STATUS_ID + " does not exist."));

            existingTicket.setStatus(status);
            existingTicket.setAnalyst(ticket.getAnalyst());
            eventPublisher.publishEvent(new TicketAnalystModifiedEvent(this, existingTicket));

            return existingTicket;
        }
        throw new Exception("The new analyst cant be the same");
    }
}
