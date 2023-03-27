package by.stolybko.servlet;

import by.stolybko.PersonService;
import by.stolybko.entity.Person;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonServletTest {

    @Test
    public void getPerson() {
        PersonServlet personServlet = new PersonServlet();
        assertEquals(new Person("Andrey", "Pilot"),
                personServlet.getPerson());
    }
}