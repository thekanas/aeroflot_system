package by.stolybko;

import by.stolybko.dao.PersonDao;
import by.stolybko.entity.Person;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonServiceTest {

    @Test
    public void getPerson() {
        PersonService personService = PersonService.getInstance();
        assertEquals(new Person("Andrey", "Pilot"),
                personService.getPerson());
    }
}