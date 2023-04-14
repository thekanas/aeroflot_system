package by.stolybko.service;

import by.stolybko.database.dao.AirplaneDao;
import by.stolybko.database.entity.Airplane;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AirplaneServiceTest {

    List<Airplane> airplaneList = new ArrayList<>() {{
        add(Airplane.builder()
                .id(1L)
                .call("BRU735")
                .make("Boeing")
                .model("737 MAX 8")
                .passengerCapacity("162")
                .flightRangeKm("6570")
                .build());

        add(Airplane.builder()
                .id(2L)
                .call("BRU729")
                .make("Boeing")
                .model("737-800")
                .passengerCapacity("189")
                .flightRangeKm("5765")
                .build());

        add(Airplane.builder()
                .id(3L)
                .call("BRU521")
                .make("Cessna")
                .model("510 Citation Mustang")
                .passengerCapacity("5")
                .flightRangeKm("2161")
                .build());

        add(Airplane.builder()
                .id(4L)
                .call("BRU655")
                .make("Embraer")
                .model("E-195")
                .passengerCapacity("124")
                .flightRangeKm("3350")
                .build());
    }};



    @Test
    public void getAll() {
        AirplaneService airplaneService = AirplaneService.getInstance();
        assertEquals(airplaneList, airplaneService.getAll());
    }

    @Test
    public void getById() {
        AirplaneService airplaneService = AirplaneService.getInstance();
        assertEquals(airplaneList.get(1), airplaneService.getById(2L));
    }
}