package com.codesync.uniticket.services;

import com.codesync.uniticket.entities.*;
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

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    UserService userService;
    @Autowired
    StatusService statusService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UnitService unitService;
    @Autowired
    FacultyService facultyService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Value("1")
    private Long NEW_TICKET_STATUS_ID;

    @Value("2")
    private Long NEW_ANALYST_STATUS_ID;

    @Value("1")
    private Long INFRASTRUCTURE_UNIT_ID;

    @Value("1")
    private Long INFRASTRUCTURE_TOPIC_ID;

    public TicketEntity saveTicket(TicketEntity ticket) {
        StatusEntity status = statusService.getStatusById(NEW_TICKET_STATUS_ID)
                .orElseThrow(() -> new EntityNotFoundException("Status with id " + NEW_TICKET_STATUS_ID + " does not exist."));
        ticket.setStatus(status);

        //Here we have to set the unit of the new ticket
        CategoryEntity category = categoryService.getCategoryById(ticket.getCategory().getId())
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + ticket.getCategory().getId() + " does not exist."));

        if (Objects.equals(category.getTopic().getId(), INFRASTRUCTURE_TOPIC_ID)) {
            UnitEntity engineeringUnit = unitService.getUnitById(INFRASTRUCTURE_UNIT_ID)
                    .orElseThrow(() -> new EntityNotFoundException("Unit with " + INFRASTRUCTURE_UNIT_ID + " does not exist."));

            ticket.setUnit(engineeringUnit);
        }

        TicketEntity savedTicket = ticketRepository.save(ticket);

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
        UserEntity existingUser = userService.getUserById(ticket.getAnalyst().getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + ticket.getAnalyst().getId() + " does not exist."));

        if (!Objects.equals(existingUser.getRole().toString(), "ANALYST")) {
            throw new Exception("The user assigned as analyst must be an analyst");
        }

        if (existingTicket.getAnalyst() != null && !Objects.equals(ticket.getAnalyst().getId(), existingTicket.getAnalyst().getId())) {
            StatusEntity status = statusService.getStatusById(NEW_ANALYST_STATUS_ID)
                    .orElseThrow(() -> new EntityNotFoundException("Status with id " + NEW_ANALYST_STATUS_ID + " does not exist."));

            existingTicket.setStatus(status);
            existingTicket.setAnalyst(ticket.getAnalyst());
            eventPublisher.publishEvent(new TicketAnalystModifiedEvent(this, existingTicket));

            //Analyze that the analyst assigned is actually an analyst

            return existingTicket;
        } else if (existingTicket.getAnalyst() == null) {
            StatusEntity status = statusService.getStatusById(NEW_ANALYST_STATUS_ID)
                    .orElseThrow(() -> new EntityNotFoundException("Status with id " + NEW_ANALYST_STATUS_ID + " does not exist."));

            existingTicket.setStatus(status);
            existingTicket.setAnalyst(ticket.getAnalyst());
            eventPublisher.publishEvent(new TicketAnalystModifiedEvent(this, existingTicket));

            return existingTicket;
        }

        throw new Exception("The analyst cannot be the same and must not be null");
    }
}
