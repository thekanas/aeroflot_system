package by.stolybko.database.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrewDTO {
    private Long captainPilot;
    private Long secondPilot;
    private Long firstSteward;
    private Long secondSteward;
    private Long thirdSteward;
    private Long fourthSteward;
    private Long fifthSteward;
    private Long sixthSteward;

    private List<Long> aircrewId = new ArrayList<>();

    public List<Long> getAircrewId() {

        if (captainPilot != null) {
            aircrewId.add(captainPilot);
        }
        if (secondPilot != null) {
            aircrewId.add(secondPilot);
        }
        if (firstSteward != null) {
            aircrewId.add(firstSteward);
        }
        if (secondSteward != null) {
            aircrewId.add(secondSteward);
        }
        if (thirdSteward != null) {
            aircrewId.add(thirdSteward);
        }
        if (fourthSteward != null) {
            aircrewId.add(fourthSteward);
        }
        if (fifthSteward != null) {
            aircrewId.add(fifthSteward);
        }
        if (sixthSteward != null) {
            aircrewId.add(sixthSteward);
        }

        return aircrewId;
    }
}
