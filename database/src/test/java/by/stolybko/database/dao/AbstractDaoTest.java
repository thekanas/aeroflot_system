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
        //session.flush();
        //flushAndClearSession();

    }



    @AfterAll
    static void afterAll() {
        //var session = sessionFactory.getSession();
        //session.getTransaction().rollback();

        var transaction = session.beginTransaction();
      /* Query query1 = session.createNativeQuery("DELETE FROM airport cascade;" +


                "DELETE FROM airport cascade;" +
                "DELETE FROM flight cascade;" +
                "DELETE FROM flight_person cascade;" +
                "DELETE FROM maker cascade;" +
                "DELETE FROM passport cascade;" +
                "DELETE FROM person cascade;");
        query1.executeUpdate();*/

        session.createNativeQuery("DELETE FROM flight", FlightEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM airplane", AirplaneEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM maker", MakerEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM passport", PassportEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM airport", AirportEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM person", PersonEntity.class).executeUpdate();
        //session.createNativeQuery("TRUNCATE TABLE person RESTART IDENTITY").executeUpdate();
        transaction.commit();
        session.close();
    }


    protected static void flushAndClearSession() {
        session.flush();
        session.clear();
    }

    protected void showContentTable(String tableName) {
        //Session session = SessionHolder.get();
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
