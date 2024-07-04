package codesync.ticketsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @OneToOne
    @JoinColumn(name = "profile")
    private ProfileEntity profile;

    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitEntity unit;

    @Enumerated(EnumType.STRING)
    private RoleEntity role;
}
