package by.stolybko.database.repository;

import by.stolybko.database.entity.AirplaneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirplaneRepository extends JpaRepository<AirplaneEntity, Long> {

    Optional<AirplaneEntity> findByModel(String model);

}
