package by.stolybko.database.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class Person {

    private Long id;
    private String fullName;
    private String position;
    private LocalDate birthDay;
    private String description;

}
