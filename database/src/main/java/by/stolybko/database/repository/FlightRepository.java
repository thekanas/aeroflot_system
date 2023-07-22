package by.stolybko.database.repository;

import by.stolybko.database.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {

    List<FlightEntity> findByTimeDepartureIsAfterAndTimeArrivalIsBeforeOrderByTimeDeparture(LocalDateTime timeAfter, LocalDateTime timeBefore);

    List<FlightEntity> findByTimeDepartureIsBeforeOrderByTimeDeparture(LocalDateTime timeBefore);
    List<FlightEntity> findByTimeDepartureIsAfterOrderByTimeDeparture(LocalDateTime timeAfter);
    //List<FlightEntity> findByTimeDepartureIsBeforeOrderByTimeDeparture(LocalDateTime timeBefore);
    List<FlightEntity> findByTimeDepartureBetweenOrderByTimeDeparture(LocalDateTime time1, LocalDateTime time2);
}
