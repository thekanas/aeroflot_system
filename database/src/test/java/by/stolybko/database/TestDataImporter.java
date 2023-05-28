package by.stolybko.database;

import by.stolybko.database.entity.Contact;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.entity.enam.Role;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;

import java.time.LocalDate;

@UtilityClass
public class TestDataImporter {
    public void importTestData(Session session) {

        var person1 = PersonEntity.builder()
                .fullName("Авдеев Ананий Максимович")
                .position("Теолог")
                .birthDay(LocalDate.of(1995,8,22))
                .description("Мифопорождающее текстовое устройство")
                .role(Role.EMPLOYEE)
                .password("123")
                .contact(new Contact("+375123", "Гомель, ул.Советская, д.5"))
                .build();

        var person2 = PersonEntity.builder()
                .fullName("Федосеев Юрий Владимирович")
                .position("Теолог")
                .birthDay(LocalDate.of(1980,6,11))
                .description("Коллективное бессознательное понимает психоз")
                .role(Role.EMPLOYEE)
                .password("000")
                .contact(new Contact("+37512345", "Гомель, ул.Советская, д.1"))
                .build();

        var person3 = PersonEntity.builder()
                .fullName("Савельева Люся Алексеевна")
                .position("Налоговый инспектор")
                .birthDay(LocalDate.of(1989,1,1))
                .description("Мифопорождающее текстовое устройство")
                .role(Role.EMPLOYEE)
                .password("   ")
                .contact(new Contact("+3751236768", "Гомель, ул.Советская, д.2"))
                .build();

        session.persist(person1);
        session.persist(person2);
        session.persist(person3);

    }
}
