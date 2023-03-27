package by.stolybko;

import by.stolybko.dao.PersonDao;
import by.stolybko.entity.Person;

public final class PersonService {

    private static final PersonService INSTANCE = new PersonService();
    private final PersonDao personDao = PersonDao.getInstance();

    private PersonService() {

    }

    public static PersonService getInstance() {
        return INSTANCE;
    }

    public Person getPerson() {
        return personDao.getDummy();
    }


}
