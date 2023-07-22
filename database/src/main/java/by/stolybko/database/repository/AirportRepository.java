package by.stolybko.database.repository;

import by.stolybko.database.entity.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<AirportEntity, Long> {

    Optional<AirportEntity> findByCodIATA(String codIATA);
}
