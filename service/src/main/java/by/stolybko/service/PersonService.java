package by.stolybko.service;

import by.stolybko.database.dao.PersonDao;
import by.stolybko.database.dto.PersonFilter;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.hibernate.HibernateFactory;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PersonService {

    private static final PersonService INSTANCE = new PersonService();
    private static final String COUNT_RECORDS_PER_PAGE = "3";
    private final PersonDao personDao = PersonDao.getInstance();
    private final HibernateFactory hibernateFactory = HibernateFactory.getInstance();

    public static PersonService getInstance() {
        return INSTANCE;
    }

    public List<PersonEntity> findAll() {
        List<PersonEntity> persons;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            persons = personDao.findAll(session);
            transaction.commit();
        }
        return persons;
    }

    public PersonEntity findById(Long id) throws Exception {
        PersonEntity person;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            person = personDao.findById(session, id).orElseThrow(Exception::new);
            System.out.println();
            transaction.commit();
        }
        return person;
    }

    public List<PersonEntity> findByFilter(PersonFilter filter, Integer page) {
        PersonFilter validFilter = createValidFilter(filter);
        List<PersonEntity> persons;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            persons = personDao.findByFilter(session, validFilter, page);
            transaction.commit();
        }
        return persons;
    }

    public Optional<PersonEntity> save(PersonEntity person) {
        Optional<PersonEntity> newPerson;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();

            newPerson = personDao.save(session, person);
            transaction.commit();
        }
        return newPerson;
    }

    public boolean delete(Long id) {
        boolean success;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            success = personDao.delete(session, id);
            transaction.commit();
        }
        return success;
    }

    public Optional<PersonEntity> update(PersonEntity person) {
        Optional<PersonEntity> updatePerson;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            updatePerson = personDao.update(session, person);
            transaction.commit();
        }
        return updatePerson;
    }

    public Long countAllRecordsByFilter(PersonFilter filter) {
        Long records;
        PersonFilter validFilter = createValidFilter(filter);
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            records = personDao.countAllRecordsByFilter(session, validFilter);
            transaction.commit();
        }
        return records;
    }

    private PersonFilter createValidFilter(PersonFilter filter) {
        PersonFilter validFilter = new PersonFilter();

        if (filter.getFullName() != null && filter.getFullName().isEmpty()) {
            validFilter.setFullName(null);
        } else {
        validFilter.setFullName(filter.getFullName());
        }

        if (filter.getPosition() != null && filter.getPosition().equalsIgnoreCase("Должность")) {
            validFilter.setPosition(null);
        } else {
            validFilter.setPosition(filter.getPosition());
        }

        if (filter.getLimit() == null || filter.getLimit().isEmpty() || Integer.parseInt(filter.getLimit()) < 1) {
            validFilter.setLimit(COUNT_RECORDS_PER_PAGE);
        } else {
            validFilter.setLimit(filter.getLimit());
        }

        try {
            Date.valueOf(filter.getBirthDayFrom());
            Date.valueOf(filter.getBirthDayTO());
            validFilter.setBirthDayFrom(filter.getBirthDayFrom());
            validFilter.setBirthDayTO(filter.getBirthDayTO());
        } catch (IllegalArgumentException e) {
            validFilter.setBirthDayFrom(null);
            validFilter.setBirthDayTO(null);
        }
        System.out.println();
        return validFilter;

    }

    public List<String> getAllPosition() {
        List<String> allPosition;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            allPosition = personDao.findAllPosition(session);
            transaction.commit();
        }
        return allPosition;
    }
}
