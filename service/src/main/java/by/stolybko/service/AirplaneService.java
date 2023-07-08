package by.stolybko.service;

import by.stolybko.database.entity.AirplaneEntity;
import by.stolybko.database.repository.AirplaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirplaneService {
    private final AirplaneRepository airplaneRepository;

    public List<AirplaneEntity> findAll() {

        return airplaneRepository.findAll();
    }
}
