package by.stolybko.database.entity;

import by.stolybko.database.entity.enam.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(exclude = {"flights", "passport"})
@Entity
@Table(name = "person")
public class PersonEntity extends CreatableEntity<Long>  {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", length = 60, nullable = false)
    private String fullName;

    @Column(name = "position", length = 60)
    private String position;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 10)
    private Role role;

    @Column(name = "password", length = 100)
    private String password;

    @Embedded
    @AttributeOverride(name = "tel", column = @Column(name = "tel"))
    @AttributeOverride(name = "address", column = @Column(name = "address"))
    private Contact contact;

    @ManyToMany(mappedBy = "aircrew", fetch = FetchType.EAGER)
    private List<FlightEntity> flights;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private PassportEntity passport;
}
