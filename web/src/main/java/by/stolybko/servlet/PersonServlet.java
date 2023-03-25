package by.stolybko.servlet;

import by.stolybko.PersonService;
import by.stolybko.entity.Person;

public class PersonServlet {
    private final PersonService personService = PersonService.getInstance();

    public Person getPerson() {
        return personService.getPerson();
    }
}
