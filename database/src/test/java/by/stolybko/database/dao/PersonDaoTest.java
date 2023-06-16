package by.stolybko.database.dao;

import by.stolybko.database.config.DatabaseConfig;
import by.stolybko.database.dto.PersonFilter;
import by.stolybko.database.entity.Contact;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.entity.enam.Role;
import by.stolybko.database.repository.PersonRepository;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DatabaseConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SqlGroup({
        @Sql(value = "classpath:test-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:purge-data.sql", executionPhase = AFTER_TEST_METHOD)
})
public class PersonDaoTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @Order(1)
    void whenFindAllInvoked_ThenAllThePersonsAreReturned() {
        String[] actual = personRepository.findAll()
                .stream()
                .map(PersonEntity::getFullName)
                .toArray(String[]::new);
        String[] expected = List.of("Калашников Аполлон Романович", "Шарова Таисия Максимовна", "Кузьмин Ян Романович")
                .toArray(String[]::new);
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(2)
    void whenFindByIdInvoked_ThenValidThePersonReturned() {
        PersonEntity testPerson = personRepository.findPersonEntitiesByFullName("Шарова Таисия Максимовна");
        Optional<PersonEntity> actual = personRepository.findById(testPerson.getId());
        assertTrue(actual.isPresent());
        assertEquals("Шарова Таисия Максимовна", actual.get().getFullName());
    }

    @Test
    @Order(3)
    void whenFindAllByFilterContainsPositionInvoked_ThenAllTheFilteredByPositionPersonsAreReturned() {
        PersonFilter filter = PersonFilter.builder()
                .position("Пилот")
                .limit("10")
                .build();
        String[] actual = personRepository.findByFilter(filter, 1)
                .stream()
                .map(PersonEntity::getFullName)
                .toArray(String[]::new);
        String[] expected = List.of("Калашников Аполлон Романович", "Кузьмин Ян Романович")
                .toArray(String[]::new);
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(4)
    void whenCreatedInvokedWithPerson_ThenPersonIsSaved() {
        PersonEntity testPerson = PersonEntity.builder()
                .fullName("Test")
                .position("Пилот")
                .birthDay(LocalDate.of(2013,12,13))
                .description("Коллективное бессознательное понимает психоз")
                .role(Role.ADMIN)
                .password("1")
                .contact(new Contact("+37512345", "Минск, ул.Советская, д.1"))
                .build();

        PersonEntity personEntity = personRepository.save(testPerson);

        List<String> allFullName = personRepository.findAll().stream()
                .map(PersonEntity::getFullName)
                .toList();
        assertTrue(allFullName.contains(testPerson.getFullName()));
    }
}
