package by.stolybko.service;

import by.stolybko.config.ServiceConfig;
import by.stolybko.database.entity.PersonEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfig.class})
class PersonServiceTest {

  /*  @Autowired
    PersonService personService;

    @Test
    void save() {
        Optional<PersonEntity> person = personService.save(
                PersonEntity.builder()
                        .fullName("Andr")
                        //.birthDay(LocalDate.parse("1.1.1990"))
                        .position("Pilot")
                        .build()
        );

        assertTrue(person.isPresent());
        assertNotNull(person.get().getId());
    }*/
}