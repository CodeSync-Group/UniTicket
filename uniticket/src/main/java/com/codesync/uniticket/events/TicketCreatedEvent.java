package com.codesync.uniticket.events;

import com.codesync.uniticket.entities.TicketEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class TicketCreatedEvent extends ApplicationEvent {
    private final TicketEntity ticket;

    public TicketCreatedEvent(Object source, TicketEntity ticket) {
        super(source);
        this.ticket = ticket;
    }

}

