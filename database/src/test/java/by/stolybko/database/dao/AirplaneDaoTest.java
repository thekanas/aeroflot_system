package by.stolybko.database.dao;

import by.stolybko.database.TestDataImporter;
import by.stolybko.database.entity.AirplaneEntity;
import by.stolybko.database.hibernate.HibernateFactory;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.*;

import javax.management.Query;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AirplaneDaoTest extends AbstractDaoTest {

    private static final AirplaneDao airplaneDao = AirplaneDao.getInstance();
    /*private static final HibernateFactory sessionFactory = HibernateFactory.getInstance();

    private static final Session session = sessionFactory.getSession();*/


    /*@BeforeAll
    static void beforeAll() {
        try (var session = sessionFactory.getSession()) {
            var transaction = session.beginTransaction();
            TestDataImporter.importTestData(session);
           transaction.commit();
        }
    }*/

    /*@BeforeAll
    static void beforeAll() {

            var transaction = session.beginTransaction();
            TestDataImporter.importTestData(session);
            // transaction.commit();
            session.flush();
            session.clear();

    }



    @AfterAll
    static void afterAll() {
        //var session = sessionFactory.getSession();
        session.getTransaction().rollback();
        session.close();

    }*/

    @Test
    @Order(1)
    void whenFindAllInvoked_ThenAllTheAirplaneAreReturned() {
        //@Cleanup Session session = sessionFactory.getSession();
        String[] actual = airplaneDao.findAll(session)
                .stream()
                .map(AirplaneEntity::getModel)
                .toArray(String[]::new);
        String[] expected = List.of("717", "727")
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

        //@Cleanup Session session = sessionFactory.getSession();
        var transaction = session.beginTransaction();
        Optional<AirplaneEntity> airplaneEntity = airplaneDao.save(session, testAirplane);
        transaction.commit();
        //flushAndClearSession();
        List<String> allFullName = airplaneDao.findAll(session).stream()
                .map(AirplaneEntity::getModel)
                .toList();
        assertTrue(allFullName.contains(testAirplane.getModel()));
    }
}
