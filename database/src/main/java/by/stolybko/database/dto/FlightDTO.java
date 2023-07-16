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
    private String airportDepartureCodIATA;
    private String airportArrivalCodIATA;
    private String airportReserveCodIATA;
    private String timeDeparture;
    private String timeArrival;
    private String airplane;

}
