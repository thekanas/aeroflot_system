package by.stolybko.service;

import by.stolybko.database.dto.CrewDTO;
import by.stolybko.database.dto.FlightDTO;
import by.stolybko.database.dto.FlightShowDTO;
import by.stolybko.database.dto.PersonDetails;
import by.stolybko.database.entity.AirplaneEntity;
import by.stolybko.database.entity.AirportEntity;
import by.stolybko.database.entity.FlightEntity;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.repository.AirplaneRepository;
import by.stolybko.database.repository.AirportRepository;
import by.stolybko.database.repository.FlightRepository;
import by.stolybko.database.repository.PersonRepository;
import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLDataException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final PersonRepository personRepository;
    private final AirportRepository airportRepository;
    private final AirplaneRepository airplaneRepository;
    private final String API_TOKEN = "3fe65063d242535469741ab25860c334";

    public FlightEntity findById(Long id) {

        return flightRepository.findById(id).get();
    }

    public List<FlightShowDTO> findAll() {
        List<FlightShowDTO> flights = new ArrayList<>();

        for (FlightEntity flight : flightRepository.findAll()) {

            flights.add(getFlightShowDTO(flight));
        }

        return flights;
    }

    public List<FlightShowDTO> findArchived() {
        List<FlightShowDTO> flights = new ArrayList<>();

        for (FlightEntity flight : flightRepository.findByTimeDepartureIsBeforeOrderByTimeDeparture(LocalDateTime.now().minusHours(10).plusSeconds(1))) {

            flights.add(getFlightShowDTO(flight));
        }

        return flights;
    }

    public List<FlightShowDTO> findAssigned() {
        List<FlightShowDTO> flights = new ArrayList<>();

        for (FlightEntity flight
                : flightRepository.findByTimeDepartureIsAfterOrderByTimeDeparture(LocalDateTime.now().plusHours(2).minusSeconds(1))) {

            flights.add(getFlightShowDTO(flight));
        }

        return flights;
    }

    public List<FlightShowDTO> findCurrentFlightsWithWeather() {
        List<FlightShowDTO> flights = new ArrayList<>();
        OpenWeatherMapClient openWeatherClient = new OpenWeatherMapClient(API_TOKEN);

        for (FlightEntity flight
                : flightRepository.findByTimeDepartureBetweenOrderByTimeDeparture(LocalDateTime.now().minusHours(10), LocalDateTime.now().plusHours(2))) {

            FlightShowDTO flightShowDTO = getFlightShowDTO(flight);

            flightShowDTO.setWeatherDeparture(openWeatherClient
                    .currentWeather()
                    .single()
                    .byCityName(flight.getAirportDeparture().getCityName())
                    .language(Language.RUSSIAN)
                    .unitSystem(UnitSystem.METRIC)
                    .retrieve()
                    .asJava());

            flightShowDTO.setWeatherArrival(openWeatherClient
                    .currentWeather()
                    .single()
                    .byCityName(flight.getAirportArrival().getCityName())
                    .language(Language.RUSSIAN)
                    .unitSystem(UnitSystem.METRIC)
                    .retrieve()
                    .asJava());

            flights.add(flightShowDTO);
        }

        return flights;
    }

    public List<FlightDTO> showAll() {
        List<FlightDTO> flightDTOList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd-MMMM-yyyy");

        for (FlightEntity flightEntity : flightRepository.findAll()) {
            flightDTOList.add(FlightDTO.builder()
                    .flightNumber(flightEntity.getFlightNumber())
                    .airportArrivalCodIATA(flightEntity.getAirportArrival().getName() + " " + flightEntity.getAirportArrival().getCodIATA())
                    .airportDepartureCodIATA(flightEntity.getAirportDeparture().getName() + " " + flightEntity.getAirportDeparture().getCodIATA())
                    .airportReserveCodIATA(flightEntity.getAirportReserve().getName() + " " + flightEntity.getAirportReserve().getCodIATA())
                    .timeArrival(flightEntity.getTimeArrival().format(dtf))
                    .timeDeparture(flightEntity.getTimeDeparture().format(dtf))
                    .airplane(flightEntity.getAirplane().getBrand().getName() + " " + flightEntity.getAirplane().getModel())
                    .build());
        }
        return flightDTOList;
    }

    public FlightEntity save(FlightDTO flight) {

        FlightEntity flightEntity = FlightEntity.builder()
                .flightNumber(flight.getFlightNumber())
                .airportArrival(airportRepository.findByCodIATA(flight.getAirportArrivalCodIATA()).get())
                .airportDeparture(airportRepository.findByCodIATA(flight.getAirportDepartureCodIATA()).get())
                .airportReserve(airportRepository.findByCodIATA(flight.getAirportReserveCodIATA()).get())
                .timeArrival(LocalDateTime.parse(flight.getTimeArrival()))
                .timeDeparture(LocalDateTime.parse(flight.getTimeDeparture()))
                .airplane(airplaneRepository.findByModel(flight.getAirplane()).get())
                .build();

        return flightRepository.save(flightEntity);
    }

    public FlightEntity update(FlightDTO flight, Long id) {

        Optional<FlightEntity> flightEntity = flightRepository.findById(id);

        if (flightEntity.isPresent()) {

            flightEntity.get().setFlightNumber(flight.getFlightNumber());
            flightEntity.get().setAirportDeparture(airportRepository.findByCodIATA(flight.getAirportDepartureCodIATA()).get());
            flightEntity.get().setAirportArrival(airportRepository.findByCodIATA(flight.getAirportArrivalCodIATA()).get());
            flightEntity.get().setAirportReserve(airportRepository.findByCodIATA(flight.getAirportReserveCodIATA()).get());
            flightEntity.get().setTimeDeparture(LocalDateTime.parse(flight.getTimeDeparture()));
            flightEntity.get().setTimeArrival(LocalDateTime.parse(flight.getTimeArrival()));
            flightEntity.get().setAirplane(airplaneRepository.findByModel(flight.getAirplane()).get());

        }
        return flightRepository.save(flightEntity.get());
    }

    public List<AirportEntity> findAllAirports() {

        return airportRepository.findAll();
    }

    public List<AirplaneEntity> findAllAirplanes() {

        return airplaneRepository.findAll();
    }

    public FlightEntity flightAddCrew(Long id, CrewDTO crew) {

        FlightEntity flight = flightRepository.findById(id).get();

        for (Long crewId : crew.getAircrewId()) {
            flight.getAircrew().add(personRepository.findById(crewId).get());
        }

        flightRepository.save(flight);

        return flight;

    }

    public FlightEntity flightUpdateCrew(CrewDTO crew, Long id) {

        FlightEntity flight = flightRepository.findById(id).get();
        flight.setAircrew(new ArrayList<>());
        for (Long crewId : crew.getAircrewId()) {
            flight.getAircrew().add(personRepository.findById(crewId).get());
        }

        flightRepository.save(flight);

        return flight;

    }

    public void delete(Long id) throws SQLDataException {

        Optional<FlightEntity> flight = flightRepository.findById(id);
        if (flight.isPresent()) {
            for (PersonEntity personEntity : flight.get().getAircrew()) {
                personEntity.getFlights().remove(flight.get());
            }
            flightRepository.deleteById(id);
        }
    }

    public FlightEntity inReserve(Long id) {

        Optional<FlightEntity> flightEntity = flightRepository.findById(id);

        flightEntity.ifPresent(entity -> entity.setAirportArrival(entity.getAirportReserve()));

        return flightRepository.save(flightEntity.get());
    }

    public FlightEntity timePlus(Long id, Integer time) {

        Optional<FlightEntity> flightEntity = flightRepository.findById(id);

        flightEntity.ifPresent(entity -> entity.setTimeDeparture(entity.getTimeDeparture().plusMinutes(time)));
        flightEntity.ifPresent(entity -> entity.setTimeArrival(entity.getTimeArrival().plusMinutes(time)));

        return flightRepository.save(flightEntity.get());
    }

    public FlightEntity timeMinus(Long id, Integer time) {

        Optional<FlightEntity> flightEntity = flightRepository.findById(id);

        flightEntity.ifPresent(entity -> entity.setTimeDeparture(entity.getTimeDeparture().minusMinutes(time)));
        flightEntity.ifPresent(entity -> entity.setTimeArrival(entity.getTimeArrival().minusMinutes(time)));

        return flightRepository.save(flightEntity.get());
    }

    public List<FlightEntity> getFlightsByPerson (PersonDetails personDetails) {
        List<FlightEntity> flightEntities = new ArrayList<>();
        Optional<PersonEntity> person = personRepository.findById(personDetails.getPerson().getId());
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getFlights());
            flightEntities = person.get().getFlights();

        }
        return flightEntities;
    }

    private FlightShowDTO getFlightShowDTO(FlightEntity flight) {
        FlightShowDTO flightShowDTO = FlightShowDTO.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airportArrival(flight.getAirportArrival())
                .airportDeparture(flight.getAirportDeparture())
                .airportReserve(flight.getAirportReserve())
                .timeArrival(flight.getTimeArrival())
                .timeDeparture(flight.getTimeDeparture())
                .airplane(flight.getAirplane())
                .aircrew(flight.getAircrew())
                .build();
        return flightShowDTO;
    }
}
