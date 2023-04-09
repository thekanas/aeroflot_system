package by.stolybko.database;

import by.stolybko.database.entity.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;
import static lombok.AccessLevel.PRIVATE;


@Getter
@NoArgsConstructor(access = PRIVATE)
public class DummyPersonDatabase {
    private static final DummyPersonDatabase INSTANCE = new DummyPersonDatabase();
    private static long personCount;

    private final Map<Long, Person> persons = new HashMap<>() {{

        put(++personCount, Person.builder()
                .id(personCount)
                .name("Andrey St")
                .position("Second pilot")
                .build());

        put(++personCount, Person.builder()
                .id(personCount)
                .name("Bob Dt")
                .position("First pilot")
                .build());

        put(++personCount, Person.builder()
                .id(personCount)
                .name("Katy Pe")
                .position("Stewardess")
                .build());
    }};

    public static DummyPersonDatabase getInstance() {
        return INSTANCE;
    }
}
