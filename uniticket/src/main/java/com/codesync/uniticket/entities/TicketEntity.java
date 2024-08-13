package com.codesync.uniticket.entities;

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

    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime creationDateTime;
    @Column(nullable = false)
    private LocalDateTime lastUpdateDateTime;

    @ManyToOne
    @JoinColumn(name = "creatorId", nullable = false)
    private UserEntity creator;

    @ManyToOne
    @JoinColumn(name = "analystId")
    private UserEntity analyst;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "statusId", nullable = false)
    private StatusEntity status;

    @ManyToOne
    @JoinColumn(name = "unitId", nullable = false)
    private UnitEntity unit;
}
