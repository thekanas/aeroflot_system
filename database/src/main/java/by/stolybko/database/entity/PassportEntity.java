package by.stolybko.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "passport")
public class PassportEntity {

    @Id
    @Column(name = "person_id")
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    @Column(name = "number", length = 20, unique = true)
    private String number;
}
