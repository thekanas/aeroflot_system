package by.stolybko.database.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonFilter {
    private String fullName;
    private String position;
    private String birthDayFrom;
    private String birthDayTO;
    private String limit;
}
