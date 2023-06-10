package by.stolybko.database.dao;

import by.stolybko.database.entity.AirplaneEntity;
import by.stolybko.database.entity.AirportEntity;
import by.stolybko.database.entity.FlightEntity;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlightDaoTest extends AbstractDaoTest {

    private static final FlightDao flightDao = FlightDao.getInstance();

    @Test
    @Order(1)
    void whenFindAllInvoked_ThenAllTheFlightAreReturned() {
        String[] actual = flightDao.findAll(session)
                .stream()
                .map(FlightEntity::getFlightNumber)
                .toArray(String[]::new);

        String[] expected = List.of("9928Y", "9828Y")
                .toArray(String[]::new);
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(2)
    void whenCreatedInvokedWithFlight_ThenFlightIsSaved() {
        showContentTable("airplane");
        showContentTable("airport");
        var transaction = session.beginTransaction();
        FlightEntity testFlight = FlightEntity.builder()
                .flightNumber("2928Y")
                .airportDeparture(session.get(AirportEntity.class, 1L))
                .airportArrival(session.get(AirportEntity.class, 2L))
                .timeDeparture(LocalDateTime.of(2023, 6, 15, 10, 0))
                .timeArrival(LocalDateTime.of(2023, 6, 15, 13, 0))
                .airplane(session.get(AirplaneEntity.class, 1L))
                .build();

        Optional<FlightEntity> flightEntity = flightDao.save(session, testFlight);
        transaction.commit();

        List<String> allFullName = flightDao.findAll(session).stream()
                .map(FlightEntity::getFlightNumber)
                .toList();
        assertTrue(allFullName.contains(testFlight.getFlightNumber()));
    }
}
