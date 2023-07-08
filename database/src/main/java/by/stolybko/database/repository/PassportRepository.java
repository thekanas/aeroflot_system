package by.stolybko.database.repository;

import by.stolybko.database.entity.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<PassportEntity, Long> {
}
