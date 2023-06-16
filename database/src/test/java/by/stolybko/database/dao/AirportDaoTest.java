package by.stolybko.database.dao;

import by.stolybko.database.config.DatabaseConfig;
import by.stolybko.database.entity.AirportEntity;
import by.stolybko.database.repository.AirportRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DatabaseConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SqlGroup({
        @Sql(value = "classpath:test-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:purge-data.sql", executionPhase = AFTER_TEST_METHOD)
})
public class AirportDaoTest {

    @Autowired
    private AirportRepository airportRepository;

    @Test
    @Order(1)
    void whenFindAllInvoked_ThenAllTheAirportAreReturned() {

        String[] actual = airportRepository.findAll()
                .stream()
                .map(AirportEntity::getCodIATA)
                .toArray(String[]::new);
        String[] expected = List.of("MSQ", "GME", "VKO")
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

        AirportEntity airportEntity = airportRepository.save(testAirport);

        List<String> allFullName = airportRepository.findAll().stream()
                .map(AirportEntity::getName)
                .toList();
        assertTrue(allFullName.contains(testAirport.getName()));
    }
}
