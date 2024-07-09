package codesync.ticketsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tickets")
public class TicketEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String description;

    private String annotation;

    private LocalDateTime creationDateTime;
    private LocalDateTime lastUpdateDateTime;

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
    @JoinColumn(name = "categoryId")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "statusId")
    private StatusEntity status;

    @ManyToOne
    @JoinColumn(name = "unitId")
    private UnitEntity unit;
}
