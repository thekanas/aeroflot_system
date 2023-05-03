package by.stolybko.service;

import by.stolybko.database.dao.PersonDao;
import by.stolybko.database.dto.PersonFilter;
import by.stolybko.database.entity.Person;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PersonService {

    private static final PersonService INSTANCE = new PersonService();
    private static final String COUNT_RECORDS_PER_PAGE = "3";
    private static final String BIRTH_DAY_FROM_DEFAULT = "1900-01-01";
    private static final String BIRTH_DAY_TO_DEFAULT = "2100-01-01";
    private final PersonDao personDao = PersonDao.getInstance();

    public static PersonService getInstance() {
        return INSTANCE;
    }

    public List<Person> findAll() {
        return personDao.findAll();
    }

    public Person findById(Long id) throws Exception {
        return personDao.findById(id)
                .orElseThrow(Exception::new);
    }

    public List<Person> findByFilter(PersonFilter filter, Integer page) {

        return personDao.findByIdFilter(createValidFilter(filter), page);
    }


    public Optional<Person> save(Person person) {
        return personDao.save(person);
    }

    public boolean delete(Long id) {
        return personDao.delete(id);
    }

    public Optional<Person> update(Person person) {
        return personDao.update(person);
    }

    public int countAllRecordsByFilter(PersonFilter filter) {

        return personDao.countAllRecordsByFilter(createValidFilter(filter));
    }

    private PersonFilter createValidFilter(PersonFilter filter) {
        PersonFilter validFilter = new PersonFilter();
        validFilter.setFullName(filter.getFullName() == null ? "" : filter.getFullName());

        if (filter.getPosition() == null || filter.getPosition().equalsIgnoreCase("Должность")) {
            validFilter.setPosition("");
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
            validFilter.setBirthDayFrom(BIRTH_DAY_FROM_DEFAULT);
            validFilter.setBirthDayTO(BIRTH_DAY_TO_DEFAULT);
        }

        return validFilter;
    }

    public List<String> getAllPosition() {
        return personDao.findAllPosition();
    }


}
