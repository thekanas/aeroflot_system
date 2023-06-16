package by.stolybko.database.repository;

import by.stolybko.database.dto.PersonFilter;
import by.stolybko.database.entity.PersonEntity;
import java.util.List;

public interface PersonRepositoryExtension {

    List<PersonEntity> findByFilter(PersonFilter filter,  Integer page);
    Long countAllRecordsByFilter(PersonFilter filter);
}
