package by.stolybko.database.dto;

import by.stolybko.database.entity.AirplaneEntity;
import by.stolybko.database.entity.AirportEntity;
import by.stolybko.database.entity.PersonEntity;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightShowDTO {

    private Long id;
    private String flightNumber;
    private AirportEntity airportDeparture;
    private AirportEntity airportArrival;
    private AirportEntity airportReserve;
    private LocalDateTime timeDeparture;
    private LocalDateTime timeArrival;
    private AirplaneEntity airplane;
    private List<PersonEntity> aircrew;
    private Weather weatherDeparture;
    private Weather weatherArrival;

}
