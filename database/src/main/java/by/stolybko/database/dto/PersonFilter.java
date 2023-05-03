package by.stolybko.database.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonFilter {
    private String fullName;
    private String position;
    private String birthDayFrom;
    private String birthDayTO;
    private String limit;
}
