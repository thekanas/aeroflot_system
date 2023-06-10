package by.stolybko.database.hibernate;

import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class HibernateFactory {

    private static final HibernateFactory INSTANCE = new HibernateFactory();

    private SessionFactory sessionFactory;

    {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public static HibernateFactory getInstance() {
        return INSTANCE;
    }
}
