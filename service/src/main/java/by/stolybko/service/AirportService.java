package by.stolybko.service;

import by.stolybko.database.entity.AirportEntity;
import by.stolybko.database.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;

    public AirportEntity save(AirportEntity airpport) {

        return airportRepository.save(airpport);
    }



}
