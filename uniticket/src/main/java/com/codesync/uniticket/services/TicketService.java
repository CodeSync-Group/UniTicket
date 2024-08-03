package com.codesync.uniticket.services;

import com.codesync.uniticket.entities.TicketEntity;
import com.codesync.uniticket.events.TicketCreatedEvent;
import com.codesync.uniticket.repositories.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public TicketEntity saveTicket(TicketEntity ticket) {
        TicketEntity savedTicket = ticketRepository.save(ticket);

        eventPublisher.publishEvent(new TicketCreatedEvent(this, savedTicket));
        return savedTicket;
    }

    public List<TicketEntity> getTickets() { return ticketRepository.findAll(); }

    @PreAuthorize("hasAnyRole('ADMIN', 'HEADUNIT', 'HEADSHIP', 'ANALYST')")
    public TicketEntity updateTicket(TicketEntity ticket) {
        TicketEntity existingTicket = ticketRepository.findById(ticket.getId())
                .orElseThrow(() -> new EntityNotFoundException("Ticket with id " + ticket.getId() + " does not exist."));

        return existingTicket;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteTicket(Long id) throws Exception {
        try {
            ticketRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
