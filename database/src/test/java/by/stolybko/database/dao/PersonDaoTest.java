package by.stolybko.database.dao;

import by.stolybko.database.entity.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersonDaoTest {

    List<Person> personList = new ArrayList<>() {{

    add(Person.builder()
                .id(1L)
                .name("Andrey St")
                .position("Second pilot")
                .build());

    add(Person.builder()
                .id(2L)
                .name("Bob Dt")
                .position("First pilot")
                .build());

    add(Person.builder()
                .id(3L)
                .name("Katy Pe")
                .position("Stewardess")
                .build());
    }};

    @Test
    public void getAll() {
        PersonDao personDao = PersonDao.getInstance();
        assertEquals(personList, personDao.getAll());
    }

    @Test
    public void getById() {
        PersonDao personDao = PersonDao.getInstance();
        assertEquals(personList.get(1), personDao.getById(2L).get());
    }
}