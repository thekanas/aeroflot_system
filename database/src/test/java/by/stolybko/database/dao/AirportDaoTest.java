package by.stolybko.database.dao;

import by.stolybko.database.entity.AirportEntity;
import org.junit.jupiter.api.*;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AirportDaoTest extends AbstractDaoTest {

    private static final AirportDao airportDao = AirportDao.getInstance();

    @Test
    @Order(1)
    void whenFindAllInvoked_ThenAllTheAirportAreReturned() {
        showContentTable("airport");
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

        var transaction = session.beginTransaction();
        Optional<AirportEntity> airportEntity = airportDao.save(session, testAirport);
        transaction.commit();

        List<String> allFullName = airportDao.findAll(session).stream()
                .map(AirportEntity::getName)
                .toList();
        assertTrue(allFullName.contains(testAirport.getName()));
    }
}
