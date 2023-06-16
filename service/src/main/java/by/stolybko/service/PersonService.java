package by.stolybko.service;

import by.stolybko.database.dto.PersonFilter;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private static final String COUNT_RECORDS_PER_PAGE = "3";
    private final PersonRepository personRepository;

    public List<PersonEntity> findAll() {

        return personRepository.findAll();
    }

    public PersonEntity findById(Long id) throws Exception {

        return personRepository.findById(id).orElseThrow(Exception::new);
    }

    public List<PersonEntity> findByFilter(PersonFilter filter, Integer page) {
        PersonFilter validFilter = createValidFilter(filter);
        return personRepository.findByFilter(validFilter, page);
    }

    public PersonEntity save(PersonEntity person) {

        return personRepository.save(person);
    }

    public void delete(Long id) {

        personRepository.deleteById(id);
    }

    public Optional<PersonEntity> update(PersonEntity person) {
        Optional<PersonEntity> updatePerson = personRepository.findById(person.getId());
        if (updatePerson.isPresent()) {
            updatePerson.get().setFullName(person.getFullName());
            updatePerson.get().setPosition(person.getPosition());
            updatePerson.get().setBirthDay(person.getBirthDay());
            updatePerson.get().setDescription(person.getDescription());
            personRepository.save(updatePerson.get());
        }

        return updatePerson;
    }

    public Long countAllRecordsByFilter(PersonFilter filter) {
        Long records;
        PersonFilter validFilter = createValidFilter(filter);
        records = personRepository.countAllRecordsByFilter(validFilter);

        return records;
    }

    public List<String> getAllPosition() {

        return personRepository.findPositions();
    }


    private PersonFilter createValidFilter(PersonFilter filter) {
        PersonFilter validFilter = new PersonFilter();

        if (filter.getFullName() != null && filter.getFullName().isEmpty()) {
            validFilter.setFullName(null);
        } else {
            validFilter.setFullName(filter.getFullName());
        }

        if (filter.getPosition() != null && filter.getPosition().equalsIgnoreCase("Должность")) {
            validFilter.setPosition(null);
        } else {
            validFilter.setPosition(filter.getPosition());
        }

        if (filter.getLimit() == null || filter.getLimit().isEmpty() || Integer.parseInt(filter.getLimit()) < 1) {
            validFilter.setLimit(COUNT_RECORDS_PER_PAGE);
        } else {
            validFilter.setLimit(filter.getLimit());
        }

        try {
            Date.valueOf(filter.getBirthDayFrom());
            Date.valueOf(filter.getBirthDayTO());
            validFilter.setBirthDayFrom(filter.getBirthDayFrom());
            validFilter.setBirthDayTO(filter.getBirthDayTO());
        } catch (IllegalArgumentException e) {
            validFilter.setBirthDayFrom(null);
            validFilter.setBirthDayTO(null);
        }
        System.out.println();
        return validFilter;

    }
}
