package codesync.ticketsystem.events;

import codesync.ticketsystem.entities.TicketEntity;
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

