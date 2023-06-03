package by.stolybko.database.dao;

import by.stolybko.database.TestDataImporter;
import by.stolybko.database.entity.AirplaneEntity;
import by.stolybko.database.entity.AirportEntity;
import by.stolybko.database.entity.FlightEntity;
import by.stolybko.database.hibernate.HibernateFactory;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlightDaoTest extends AbstractDaoTest {

    private static final FlightDao flightDao = FlightDao.getInstance();
    /*private static final HibernateFactory sessionFactory = HibernateFactory.getInstance();*/

    /*@BeforeAll
    static void beforeAll() {
        try (var session = sessionFactory.getSession()) {
            var transaction = session.beginTransaction();
            TestDataImporter.importTestData(session);
            transaction.commit();
        }
    }*/

    @Test
    @Order(1)
    void whenFindAllInvoked_ThenAllTheFlightAreReturned() {
        //@Cleanup Session session = sessionFactory.getSession();
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
        //@Cleanup Session session = sessionFactory.getSession();
        showContentTable("airplane");
        showContentTable("airport");
        var transaction = session.beginTransaction();
        FlightEntity testFlight = FlightEntity.builder()
                .flightNumber("2928Y")
                .airportDeparture(session.get(AirportEntity.class, 6L))
                .airportArrival(session.get(AirportEntity.class, 7L))
                .timeDeparture(LocalDateTime.of(2023, 6, 15, 10, 0))
                .timeArrival(LocalDateTime.of(2023, 6, 15, 13, 0))
                .airplane(session.get(AirplaneEntity.class, 6L))
                .build();
        //System.out.println(testFlight);

        Optional<FlightEntity> flightEntity = flightDao.save(session, testFlight);
        transaction.commit();
        //flushAndClearSession();

        List<String> allFullName = flightDao.findAll(session).stream()
                .map(FlightEntity::getFlightNumber)
                .toList();
        assertTrue(allFullName.contains(testFlight.getFlightNumber()));
    }
}
