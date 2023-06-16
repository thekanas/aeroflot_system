package by.stolybko.service;

import by.stolybko.database.entity.FlightEntity;
import by.stolybko.database.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public List<FlightEntity> findAll() {

        return flightRepository.findAll();
    }
}
