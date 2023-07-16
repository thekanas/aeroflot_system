package by.stolybko.service;


import by.stolybko.database.dto.AirplaneDTO;
import by.stolybko.database.entity.AirplaneEntity;
import by.stolybko.database.entity.AirportEntity;
import by.stolybko.database.entity.BrandEntity;
import by.stolybko.database.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;

    public AirportEntity save(AirportEntity airpport) {

        return airportRepository.save(airpport);
    }



}
