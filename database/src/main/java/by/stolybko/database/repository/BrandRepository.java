package by.stolybko.database.repository;

import by.stolybko.database.entity.AirplaneEntity;
import by.stolybko.database.entity.BrandEntity;
import by.stolybko.database.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    Optional<BrandEntity> findByName(String name);

}
