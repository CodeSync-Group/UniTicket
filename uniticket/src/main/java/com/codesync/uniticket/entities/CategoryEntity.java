package com.codesync.uniticket.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "facultyId", nullable = false)
    private FacultyEntity faculty;

    @ManyToOne
    @JoinColumn(name = "departmentId", nullable = false)
    private DepartmentEntity department;

    @ManyToOne
    @JoinColumn(name = "topicId", nullable = false)
    private TopicEntity topic;
}
