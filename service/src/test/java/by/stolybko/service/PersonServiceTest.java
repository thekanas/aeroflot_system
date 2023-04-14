package by.stolybko.service;

import by.stolybko.database.dao.PersonDao;
import by.stolybko.database.entity.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PersonServiceTest {
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
        PersonService personService = PersonService.getInstance();
        assertEquals(personList, personService.getAll());
    }

    @Test
    public void getById() {
        PersonService personService = PersonService.getInstance();
        assertEquals(personList.get(1), personService.getById(2L));
    }

}