package by.stolybko.service;

import by.stolybko.database.dto.FlightDTO;
import by.stolybko.database.entity.FlightEntity;
import by.stolybko.database.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public List<FlightEntity> findAll() {

        return flightRepository.findAll();
    }

    public List<FlightDTO> showAll() {
        List<FlightDTO> flightDTOList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd-MMMM-yyyy");

        for (FlightEntity flightEntity : flightRepository.findAll()) {
            flightDTOList.add(FlightDTO.builder()
                            .flightNumber(flightEntity.getFlightNumber())
                            .airportArrival(flightEntity.getAirportArrival().getName() + " " + flightEntity.getAirportArrival().getCodIATA())
                            .airportDeparture(flightEntity.getAirportDeparture().getName() + " " + flightEntity.getAirportDeparture().getCodIATA())
                            .airportReserve(flightEntity.getAirportReserve().getName() + " " + flightEntity.getAirportReserve().getCodIATA())
                            .timeArrival(flightEntity.getTimeArrival().format(dtf))
                            .timeDeparture(flightEntity.getTimeDeparture().format(dtf))
                            .airplane(flightEntity.getAirplane().getBrand().getName() + " " + flightEntity.getAirplane().getModel())
                    .build());
        }
        return flightDTOList;
    }
}
