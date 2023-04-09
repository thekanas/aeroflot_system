package by.stolybko.database.entity;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Airplane {
    private Long id;
    private String call;
    private String make;
    private String model;
    private String passengerCapacity;
    private String flightRangeKm;

}
