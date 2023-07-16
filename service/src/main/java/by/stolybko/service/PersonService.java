package by.stolybko.service;

import by.stolybko.database.dto.PersonDTO;
import by.stolybko.database.dto.PersonDetails;
import by.stolybko.database.dto.PersonFilter;
import by.stolybko.database.entity.PassportEntity;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.repository.PassportRepository;
import by.stolybko.database.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService implements UserDetailsService {

    private static final String COUNT_RECORDS_PER_PAGE = "3";
    private final PersonRepository personRepository;
    private final PassportRepository passportRepository;
    private final PasswordEncoder passwordEncoder;

    public List<PersonEntity> findAll() {

        return personRepository.findAll();
    }
    public List<PersonEntity> findPilots() {

        return personRepository.findByPosition("Пилот");
    }
    public List<PersonEntity> findStewards() {

        return personRepository.findByPosition("Бортпроводник");
    }

    public PersonEntity findById(Long id) throws Exception {

        return personRepository.findById(id).orElseThrow(Exception::new);
    }

    public List<PersonEntity> findByFilter(PersonFilter filter, Integer page) {
        PersonFilter validFilter = createValidFilter(filter);
        return personRepository.findByFilter(validFilter, page);
    }

    public PersonEntity save(PersonDTO person) {


        PersonEntity personEntity = PersonEntity.builder()
                .fullName(person.getFullName())
                .position(person.getPosition())
                .birthDay(person.getBirthDay())
                .description(person.getDescription())
                .role(person.getRole())
                .password(passwordEncoder.encode(person.getPassword()))
                .contact(person.getContact())
                .build();

        PersonEntity personEntitySaved = personRepository.save(personEntity);
        PassportEntity passport = new PassportEntity();
        passport.setNumber(person.getPassportNumber());
        passport.setPerson(personEntitySaved);
        passportRepository.save(passport);

        return personEntitySaved;
    }

    public void delete(Long id) {

        personRepository.deleteById(id);
    }

    public Optional<PersonEntity> update(PersonDTO person, Long id) {
        Optional<PersonEntity> updatePerson = personRepository.findById(id);
        if (updatePerson.isPresent()) {
            updatePerson.get().setFullName(person.getFullName());
            updatePerson.get().setPosition(person.getPosition());
            updatePerson.get().setBirthDay(person.getBirthDay());
            updatePerson.get().setRole(person.getRole());
            updatePerson.get().setContact(person.getContact());
            if (person.getPassword() != null) {

                updatePerson.get().setPassword(passwordEncoder.encode(person.getPassword()));
            }
            updatePerson.get().setDescription(person.getDescription());

            Optional<PassportEntity> updatePassport = passportRepository.findById(id);
            if (person.getPassportNumber() != null && updatePassport.isPresent()) {
                updatePassport.get().setNumber(person.getPassportNumber());
                passportRepository.save(updatePassport.get());
            }

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

    @Override
    public UserDetails loadUserByUsername(String tel) throws UsernameNotFoundException {
        Optional<PersonEntity> person = personRepository.findPersonEntitiesByContactTel(tel);

        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new PersonDetails(person.get());
    }
}
