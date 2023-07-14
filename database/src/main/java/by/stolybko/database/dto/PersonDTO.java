package by.stolybko.database.dto;

import by.stolybko.database.entity.Contact;
import by.stolybko.database.entity.enam.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private String fullName;
    private String position;
    private LocalDate birthDay;
    private String description;
    private Role role;
    private String password;
    private Contact contact;
    private String passportNumber;
}
