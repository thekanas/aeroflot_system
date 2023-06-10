package by.stolybko.service;

import by.stolybko.database.dao.AirplaneDao;
import by.stolybko.database.entity.AirplaneEntity;
import by.stolybko.database.hibernate.HibernateFactory;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AirplaneService {

    private static final AirplaneService INSTANCE = new AirplaneService();
    private final AirplaneDao airplaneDao = AirplaneDao.getInstance();
    private final HibernateFactory hibernateFactory = HibernateFactory.getInstance();

    public List<AirplaneEntity> findAll() {
        List<AirplaneEntity> airplans;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            airplans = airplaneDao.findAll(session);
            transaction.commit();
        }
        return airplans;
    }

    public static AirplaneService getInstance() {
        return INSTANCE;
    }
}
