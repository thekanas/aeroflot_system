package by.stolybko.database;

import by.stolybko.database.entity.*;
import by.stolybko.database.entity.enam.Role;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

        var airport1 = AirportEntity.builder()
                .name("аэропорт Гомель")
                .codIATA("GME")
                .build();

        var airport2 = AirportEntity.builder()
                .name("аэропорт Москва")
                .codIATA("MSK")
                .build();

        session.persist(airport1);
        session.persist(airport2);

        var airplane1 = AirplaneEntity.builder()
                .model("717")
                .flightRangeKm(1000)
                .passengerCapacity(150)
                .build();

        var airplane2 = AirplaneEntity.builder()
                .model("727")
                .flightRangeKm(1100)
                .passengerCapacity(160)
                .build();

        session.persist(airplane1);
        session.persist(airplane2);

        var flight1 = FlightEntity.builder()
                .flightNumber("9928Y")
                .airportDeparture(airport1)
                .airportArrival(airport2)
                .timeDeparture(LocalDateTime.of(2023, 6, 13, 10, 0))
                .timeArrival(LocalDateTime.of(2023, 6, 13, 13, 0))
                .airplane(airplane1)
                .build();

        var flight2 = FlightEntity.builder()
                .flightNumber("9828Y")
                .airportDeparture(airport2)
                .airportArrival(airport1)
                .timeDeparture(LocalDateTime.of(2023, 6, 18, 10, 0))
                .timeArrival(LocalDateTime.of(2023, 6, 18, 13, 0))
                .airplane(airplane2)
                .build();

        session.persist(flight1);
        session.persist(flight2);
    }
}
