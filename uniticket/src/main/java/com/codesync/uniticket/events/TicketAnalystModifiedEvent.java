package com.codesync.uniticket.events;

import com.codesync.uniticket.entities.TicketEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TicketAnalystModifiedEvent extends ApplicationEvent {
    private final TicketEntity ticket;

    public TicketAnalystModifiedEvent(Object source, TicketEntity ticket) {
        super(source);
        this.ticket = ticket;
    }
}
