package by.stolybko.database.entity;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Airplane {
    private Long id;
    private String make;
    private String model;
    private int flightRangeKm;
    private int passengerCapacity;

}
