package by.stolybko.database;

import by.stolybko.database.entity.Airplane;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;
import static lombok.AccessLevel.PRIVATE;


@Getter
@NoArgsConstructor(access = PRIVATE)
public class DummyAirplaneDatabase {

    private static final DummyAirplaneDatabase INSTANCE = new DummyAirplaneDatabase();
    private static long airplaneCount;

    private final Map<Long, Airplane> airplanes = new HashMap<>() {{

        put(++airplaneCount, Airplane.builder()
                .id(airplaneCount)
                .call("BRU735")
                .make("Boeing")
                .model("737 MAX 8")
                .passengerCapacity("162")
                .flightRangeKm("6570")
                .build());

        put(++airplaneCount, Airplane.builder()
                .id(airplaneCount)
                .call("BRU729")
                .make("Boeing")
                .model("737-800")
                .passengerCapacity("189")
                .flightRangeKm("5765")
                .build());

        put(++airplaneCount, Airplane.builder()
                .id(airplaneCount)
                .call("BRU521")
                .make("Cessna")
                .model("510 Citation Mustang")
                .passengerCapacity("5")
                .flightRangeKm("2161")
                .build());

        put(++airplaneCount, Airplane.builder()
                .id(airplaneCount)
                .call("BRU655")
                .make("Embraer")
                .model("E-195")
                .passengerCapacity("124")
                .flightRangeKm("3350")
                .build());

    }};


    public static DummyAirplaneDatabase getInstance() {
        return INSTANCE;
    }
}
