package by.stolybko.database.dao;

import by.stolybko.database.TestDataImporter;
import by.stolybko.database.entity.*;
import by.stolybko.database.hibernate.HibernateFactory;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import java.util.List;

public class AbstractDaoTest {

    protected static HibernateFactory sessionFactory = null;
    protected static Session session = null;

    @BeforeAll
    static void beforeAll() {
        sessionFactory = HibernateFactory.getInstance();
        session = sessionFactory.getSession();
        var transaction = session.beginTransaction();
        TestDataImporter.importTestData(session);
         transaction.commit();
    }

    @AfterAll
    static void afterAll() {
        var transaction = session.beginTransaction();

        session.createNativeQuery("DELETE FROM flight", FlightEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM airplane", AirplaneEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM maker", BrandEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM passport", PassportEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM airport", AirportEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM person", PersonEntity.class).executeUpdate();
        //session.createNativeQuery("TRUNCATE TABLE person RESTART IDENTITY").executeUpdate();
        transaction.commit();
        session.close();
    }

    protected void showContentTable(String tableName) {
        Query query1 = session.createNativeQuery("select * from " + tableName);
        List<Object[]> results = query1.getResultList();
        System.out.println("\n======================================");
        System.out.println("Content table \"" + tableName + "\":");
        System.out.println("--------------------------------------");
        int cnt = 0;
        for (Object[] obj : results) {
            cnt++;
            System.out.println("Row: " + cnt);
            for (Object objItem: obj) {
                System.out.println("\t" + objItem);
            }
        }
        System.out.println("======================================\n");
    }
}
