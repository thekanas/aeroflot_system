package by.stolybko.service;

import by.stolybko.database.dao.PersonDao;
import by.stolybko.database.entity.Person;
import lombok.NoArgsConstructor;
import java.util.List;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PersonService {

    private static final PersonService INSTANCE = new PersonService();
    private final PersonDao personDao = PersonDao.getInstance();

    public List<Person> getAll() {
        return personDao.getAll();
    }

    public Person getById(Long id) {
        return personDao.getById(id)
                .orElseThrow(RuntimeException::new);
    }


    public static PersonService getInstance() {
        return INSTANCE;
    }




}
