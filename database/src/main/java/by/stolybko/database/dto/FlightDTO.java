package by.stolybko.database.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {

    private String flightNumber;
    private String airportDeparture;
    private String airportArrival;
    private String airportReserve;
    private String timeDeparture;
    private String timeArrival;
    private String airplane;

}
