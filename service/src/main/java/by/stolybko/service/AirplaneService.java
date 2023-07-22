package by.stolybko.service;

import by.stolybko.database.dto.AirplaneDTO;
import by.stolybko.database.entity.AirplaneEntity;
import by.stolybko.database.entity.BrandEntity;
import by.stolybko.database.repository.AirplaneRepository;
import by.stolybko.database.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.SQLDataException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirplaneService {
    private final AirplaneRepository airplaneRepository;
    private final BrandRepository brandRepository;

    public List<AirplaneEntity> findAll() {

        return airplaneRepository.findAll();
    }

    public AirplaneEntity save(AirplaneDTO airplane) {

        Optional<BrandEntity> brand = brandRepository.findByName(airplane.getBrand());

        AirplaneEntity airplaneEntity = AirplaneEntity.builder()
                .model(airplane.getModel())
                .flightRangeKm(airplane.getFlightRangeKm())
                .passengerCapacity(airplane.getPassengerCapacity())
                .build();

        if (brand.isPresent()) {
            airplaneEntity.setBrand(brand.get());
        } else {
            airplaneEntity.setBrand(brandRepository.save(new BrandEntity(airplane.getBrand())));
        }

        return airplaneRepository.save(airplaneEntity);
    }

    public AirplaneEntity findById(Long id) throws Exception {

        return airplaneRepository.findById(id).orElseThrow(Exception::new);
    }

    public Optional<AirplaneEntity> update(AirplaneDTO airplane, Long id) {
        Optional<AirplaneEntity> updateAirplane = airplaneRepository.findById(id);
        Optional<BrandEntity> brand = brandRepository.findByName(airplane.getBrand());

        if (updateAirplane.isPresent()) {

            updateAirplane.get().setModel(airplane.getModel());
            updateAirplane.get().setFlightRangeKm(airplane.getFlightRangeKm());
            updateAirplane.get().setPassengerCapacity(airplane.getPassengerCapacity());
            updateAirplane.get().setImg(airplane.getImg());

            if (brand.isPresent()) {
                updateAirplane.get().setBrand(brand.get());
            } else {
                updateAirplane.get().setBrand(brandRepository.save(new BrandEntity(airplane.getBrand())));
            }

            airplaneRepository.save(updateAirplane.get());

        }

        return updateAirplane;
    }

    public void delete(Long id) throws SQLDataException {

        airplaneRepository.deleteById(id);
    }
}
