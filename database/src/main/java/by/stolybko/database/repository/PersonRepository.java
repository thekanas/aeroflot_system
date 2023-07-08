package by.stolybko.database.repository;

import by.stolybko.database.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long>, PersonRepositoryExtension {


    @Query("select distinct p.position from PersonEntity p")
    List<String> findPositions();

    Optional<PersonEntity> findPersonEntitiesByFullName(String fullName);
    Optional<PersonEntity> findPersonEntitiesByContactTel(String tel);


}
