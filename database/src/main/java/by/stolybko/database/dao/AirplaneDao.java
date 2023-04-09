package by.stolybko.database.dao;


import by.stolybko.database.DummyAirplaneDatabase;
import by.stolybko.database.entity.Airplane;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AirplaneDao {
    private static final AirplaneDao INSTANCE = new AirplaneDao();
    private final DummyAirplaneDatabase db = DummyAirplaneDatabase.getInstance();

    public List<Airplane> getAll() {
        return new ArrayList<>(db.getAirplanes().values());
    }

    public Optional<Airplane> getById(Long id) {
        return Optional.ofNullable(db.getAirplanes().get(id));
    }

    public Airplane create(Airplane airplane) {
        return db.getAirplanes().put(airplane.getId(), airplane);
    }

    public Airplane delete(Long id) {
        return Optional.ofNullable(db.getAirplanes().remove(id))
                .orElseThrow(RuntimeException::new);
    }

    public static AirplaneDao getInstance() {
        return INSTANCE;
    }
}
