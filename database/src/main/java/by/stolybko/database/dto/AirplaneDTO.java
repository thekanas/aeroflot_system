package by.stolybko.database.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneDTO {

    private String brand;
    private String model;
    private int flightRangeKm;
    private int passengerCapacity;
    private String img;
}
