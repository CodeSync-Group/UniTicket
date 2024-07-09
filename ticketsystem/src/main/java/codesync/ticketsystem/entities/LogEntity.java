package codesync.ticketsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "logs")
public class LogEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private RoleEntity role;

    private String annotation;

    @ManyToOne
    @JoinColumn(name = "creatorId")
    private UserEntity creator;

    @ManyToOne
    @JoinColumn(name = "headshipId")
    private UserEntity headship;

    @ManyToOne
    @JoinColumn(name = "analystId")
    private UserEntity analyst;

    @ManyToOne
    @JoinColumn(name = "ticketId")
    private TicketEntity ticket;

    @ManyToOne
    @JoinColumn(name = "unitId")
    private UnitEntity unit;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "statusId")
    private StatusEntity status;
}
