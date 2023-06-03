package by.stolybko.database.dao;

import by.stolybko.database.TestDataImporter;
import by.stolybko.database.dto.PersonFilter;
import by.stolybko.database.entity.Contact;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.entity.enam.Role;
import by.stolybko.database.hibernate.HibernateFactory;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonDaoTest extends AbstractDaoTest {

    private static final PersonDao personDao = PersonDao.getInstance();
    /*private static final HibernateFactory sessionFactory = HibernateFactory.getInstance();

    @BeforeAll
    static void beforeAll() {
        try (var session = sessionFactory.getSession()) {
            var transaction = session.beginTransaction();
            TestDataImporter.importTestData(session);
            transaction.commit();
        }
    }*/



    @Test
    @Order(1)
    void whenFindAllInvoked_ThenAllThePersonsAreReturned() {
        //@Cleanup Session session = sessionFactory.getSession();
        String[] actual = personDao.findAll(session)
                .stream()
                .map(PersonEntity::getFullName)
                .toArray(String[]::new);
        String[] expected = List.of("Авдеев Ананий Максимович", "Федосеев Юрий Владимирович", "Савельева Люся Алексеевна")
                .toArray(String[]::new);
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(2)
    void whenFindByIdInvoked_ThenValidThePersonReturned() {
        //@Cleanup Session session = sessionFactory.getSession();
        showContentTable("person");
        Optional<PersonEntity> actual = personDao.findById(session, 10L);
        System.out.println(session.get(PersonEntity.class, 1L));
        assertTrue(actual.isPresent());
        assertEquals("Авдеев Ананий Максимович", actual.get().getFullName());
    }

    @Test
    @Order(3)
    void whenFindAllByFilterContainsPositionInvoked_ThenAllTheFilteredByPositionPersonsAreReturned() {
        //@Cleanup Session session = sessionFactory.getSession();
        PersonFilter filter = PersonFilter.builder()
                .position("Теолог")
                .limit("10")
                .build();
        String[] actual = personDao.findByFilter(session, filter, 1)
                .stream()
                .map(PersonEntity::getFullName)
                .toArray(String[]::new);
        String[] expected = List.of("Авдеев Ананий Максимович", "Федосеев Юрий Владимирович")
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

        //@Cleanup Session session = sessionFactory.getSession();
        var transaction = session.beginTransaction();
        Optional<PersonEntity> personEntity = personDao.save(session, testPerson);
        transaction.commit();
        //flushAndClearSession();

        List<String> allFullName = personDao.findAll(session).stream()
                .map(PersonEntity::getFullName)
                .toList();
        assertTrue(allFullName.contains(testPerson.getFullName()));
    }
}
