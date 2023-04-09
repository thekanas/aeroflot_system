package by.stolybko.database.dao;

import by.stolybko.database.DummyPersonDatabase;
import by.stolybko.database.entity.Person;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class PersonDao {

    private static final PersonDao INSTANCE = new PersonDao();
    private final DummyPersonDatabase db = DummyPersonDatabase.getInstance();

    public List<Person> getAll() {
        return new ArrayList<>(db.getPersons().values());
    }

    public Optional<Person> getById(Long id) {
        return Optional.ofNullable(db.getPersons().get(id));
    }

    public Person create(Person person) {
        return db.getPersons().put(person.getId(), person);
    }

    public Person delete(Long id) {
        return Optional.ofNullable(db.getPersons().remove(id))
                .orElseThrow(RuntimeException::new);
    }

    public static PersonDao getInstance() {
        return INSTANCE;
    }
}
