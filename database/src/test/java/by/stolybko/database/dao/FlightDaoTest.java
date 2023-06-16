package by.stolybko.database.dao;

import by.stolybko.database.config.DatabaseConfig;
import by.stolybko.database.entity.AirplaneEntity;
import by.stolybko.database.entity.AirportEntity;
import by.stolybko.database.entity.FlightEntity;
import by.stolybko.database.repository.AirplaneRepository;
import by.stolybko.database.repository.AirportRepository;
import by.stolybko.database.repository.FlightRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DatabaseConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SqlGroup({
        @Sql(value = "classpath:test-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:purge-data.sql", executionPhase = AFTER_TEST_METHOD)
})
public class FlightDaoTest {

    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private AirplaneRepository airplaneRepository;

    @Test
    @Order(1)
    void whenFindAllInvoked_ThenAllTheFlightAreReturned() {
        String[] actual = flightRepository.findAll()
                .stream()
                .map(FlightEntity::getFlightNumber)
                .toArray(String[]::new);

        String[] expected = List.of("2989R", "2990L")
                .toArray(String[]::new);
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(2)
    void whenCreatedInvokedWithFlight_ThenFlightIsSaved() {
        AirportEntity testAirport1 = AirportEntity.builder()
                .name("аэропорт Болгария")
                .codIATA("BLG")
                .build();
        AirportEntity testAirport2 = AirportEntity.builder()
                .name("аэропорт Турция")
                .codIATA("TTG")
                .build();
        AirplaneEntity testAirplane = AirplaneEntity.builder()
                .model("747")
                .flightRangeKm(2000)
                .passengerCapacity(250)
                .build();
        airportRepository.save(testAirport1);
        airportRepository.save(testAirport2);
        airplaneRepository.save(testAirplane);

        FlightEntity testFlight = FlightEntity.builder()
                .flightNumber("2928Y")
                .airportDeparture(testAirport1)
                .airportArrival(testAirport2)
                .timeDeparture(LocalDateTime.of(2023, 6, 15, 10, 0))
                .timeArrival(LocalDateTime.of(2023, 6, 15, 13, 0))
                .airplane(testAirplane)
                .build();

        FlightEntity flightEntity = flightRepository.save(testFlight);

        List<String> allFullName = flightRepository.findAll().stream()
                .map(FlightEntity::getFlightNumber)
                .toList();
        assertTrue(allFullName.contains(testFlight.getFlightNumber()));
    }

}
