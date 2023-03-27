package by.stolybko.dao;

import by.stolybko.entity.Person;

import static org.junit.Assert.*;

public class PersonDaoTest {

    @org.junit.Test
    public void getDummy() {
        PersonDao personDao = PersonDao.getInstance();
        assertEquals(new Person("Andrey", "Pilot"),
                personDao.getDummy());
    }
}