package codesync.ticketsystem.entities;

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
    @JoinColumn(name = "facultyId")
    private FacultyEntity faculty;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    private DepartmentEntity department;

    @ManyToOne
    @JoinColumn(name = "topicId")
    private TopicEntity topic;
}
