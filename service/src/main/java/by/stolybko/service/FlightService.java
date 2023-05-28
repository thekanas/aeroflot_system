package by.stolybko.service;

import by.stolybko.database.dao.FlightDao;
import by.stolybko.database.entity.FlightEntity;
import by.stolybko.database.hibernate.HibernateFactory;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class FlightService {
    private static final FlightService INSTANCE = new FlightService();
    private final FlightDao flightDao = FlightDao.getInstance();
    private final HibernateFactory hibernateFactory = HibernateFactory.getInstance();

    public List<FlightEntity> findAll() {
        List<FlightEntity> flights;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            flights = flightDao.findAll(session);
            transaction.commit();
        }
        return flights;
    }

    public static FlightService getInstance() {
        return INSTANCE;
    }
}
