package codesync.ticketsystem.controllers;

import codesync.ticketsystem.entities.TicketEntity;
import codesync.ticketsystem.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @Operation(summary = "Save a ticket")
    @PostMapping("/")
    public ResponseEntity<TicketEntity> saveTicket(@RequestBody TicketEntity ticket) {
        TicketEntity savedTicket = ticketService.saveTicket(ticket);
        return ResponseEntity.ok(savedTicket);
    }

    @Operation(summary = "Get tickets")
    @GetMapping("/")
    public ResponseEntity<List<TicketEntity>> getTickets() {
        List<TicketEntity> tickets = ticketService.getTickets();
        return ResponseEntity.ok(tickets);
    }
}
