package com.codesync.uniticket.entities;

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

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEntity role;

    @Column(nullable = false)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "creatorId", nullable = false)
    private UserEntity creator;

    @ManyToOne
    @JoinColumn(name = "analystId")
    private UserEntity analyst;

    @ManyToOne
    @JoinColumn(name = "ticketId", nullable = false)
    private TicketEntity ticket;

    @ManyToOne
    @JoinColumn(name = "unitId", nullable = false)
    private UnitEntity unit;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "statusId", nullable = false)
    private StatusEntity status;
}
