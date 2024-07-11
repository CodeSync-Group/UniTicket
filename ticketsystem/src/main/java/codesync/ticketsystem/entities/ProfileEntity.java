package codesync.ticketsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profiles")
public class ProfileEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String secondSurname;

    @Column(unique = true, nullable = false)
    private String email;

    private String description;

    private String gender;

    private LocalDate birthday;

    @Column(columnDefinition="bytea")
    private byte[] picture;
}
