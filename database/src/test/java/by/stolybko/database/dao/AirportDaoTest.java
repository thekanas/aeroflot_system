package by.stolybko.database.dao;

import by.stolybko.database.TestDataImporter;
import by.stolybko.database.entity.AirportEntity;
import by.stolybko.database.hibernate.HibernateFactory;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AirportDaoTest {

    private static final AirportDao airportDao = AirportDao.getInstance();
    private static final HibernateFactory sessionFactory = HibernateFactory.getInstance();

    @BeforeAll
    static void beforeAll() {
        try (var session = sessionFactory.getSession()) {
            var transaction = session.beginTransaction();
            TestDataImporter.importTestData(session);
            transaction.commit();
        }
    }

    @Test
    @Order(1)
    void whenFindAllInvoked_ThenAllTheAirportAreReturned() {
        @Cleanup Session session = sessionFactory.getSession();
        String[] actual = airportDao.findAll(session)
                .stream()
                .map(AirportEntity::getCodIATA)
                .toArray(String[]::new);
        String[] expected = List.of("GME", "MSK")
                .toArray(String[]::new);
        assertArrayEquals(expected, actual);
    }


    @Test
    @Order(2)
    void whenCreatedInvokedWithAirport_ThenAirportIsSaved() {
        AirportEntity testAirport = AirportEntity.builder()
                .name("аэропорт Болгария")
                .codIATA("BLG")
                .build();

        @Cleanup Session session = sessionFactory.getSession();
        var transaction = session.beginTransaction();
        Optional<AirportEntity> airportEntity = airportDao.save(session, testAirport);
        transaction.commit();

        List<String> allFullName = airportDao.findAll(session).stream()
                .map(AirportEntity::getName)
                .toList();
        assertTrue(allFullName.contains(testAirport.getName()));
    }
}
