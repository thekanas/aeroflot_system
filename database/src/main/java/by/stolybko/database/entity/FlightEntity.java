package by.stolybko.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "flight")
public class FlightEntity implements BaseEntity<Long> {

    @Id
    @Column(name = "flight_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    private String flightNumber;

    @ManyToOne
    @JoinColumn(name = "airport_departure_id", nullable = false, referencedColumnName = "airport_id")
    private AirportEntity airportDeparture;

    @ManyToOne
    @JoinColumn(name = "airport_arrival_id", nullable = false, referencedColumnName = "airport_id")
    private AirportEntity airportArrival;

    @ManyToOne
    @JoinColumn(name = "airport_reserve_id", referencedColumnName = "airport_id")
    private AirportEntity airportReserve;

    @Column(name = "time_departure", nullable = false)
    private LocalDateTime timeDeparture;

    @Column(name = "time_arrival", nullable = false)
    private LocalDateTime timeArrival;

    @ManyToOne
    @JoinColumn(name = "airplane_id", nullable = false, referencedColumnName = "airplane_id")
    private AirplaneEntity airplane;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "flight_person",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<PersonEntity> aircrew;
}
