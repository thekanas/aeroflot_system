package by.stolybko.database.dao;

import by.stolybko.database.config.DatabaseConfig;
import by.stolybko.database.entity.AirplaneEntity;
import by.stolybko.database.repository.AirplaneRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
public class AirplaneDaoTest {

    @Autowired
    private AirplaneRepository airplaneRepository;

    @Test
    @Order(1)
    void whenFindAllInvoked_ThenAllTheAirplaneAreReturned() {
         String[] actual = airplaneRepository.findAll()
                .stream()
                .map(AirplaneEntity::getModel)
                .toArray(String[]::new);
        String[] expected = List.of("737 MAX 8", "737-800")
                .toArray(String[]::new);
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(2)
    void whenCreatedInvokedWithAirplane_ThenAirplaneIsSaved() {
        AirplaneEntity testAirplane = AirplaneEntity.builder()
                .model("747")
                .flightRangeKm(2000)
                .passengerCapacity(250)
                .build();

        AirplaneEntity airplaneEntity = airplaneRepository.save(testAirplane);

        List<String> allFullName = airplaneRepository.findAll().stream()
                .map(AirplaneEntity::getModel)
                .toList();
        assertTrue(allFullName.contains(testAirplane.getModel()));
    }
}
