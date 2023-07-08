package by.stolybko.web.controller;

import by.stolybko.database.dto.FlightDTO;
import by.stolybko.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestController {

    private final FlightService flightService;

    @GetMapping("/flights")
    public ResponseEntity<List<FlightDTO>> showFlightAPI() {
        return ResponseEntity.ok(flightService.showAll());
    }

}
