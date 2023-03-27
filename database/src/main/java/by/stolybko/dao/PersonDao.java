package by.stolybko.dao;

import by.stolybko.entity.Person;

public final class PersonDao {
    private static final PersonDao INSTANCE = new PersonDao();

    private PersonDao() {

    }

    public static PersonDao getInstance() {
        return INSTANCE;
    }

    public Person getDummy() {
        return new Person("Andrey", "Pilot");
    }
}
