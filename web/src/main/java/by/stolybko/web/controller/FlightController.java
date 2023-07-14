package by.stolybko.web.controller;


import by.stolybko.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static by.stolybko.web.util.PagesUtil.FLIGHTS;

@Controller
@RequestMapping(FLIGHTS)
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public String showFlights(Model model) {
        model.addAttribute("flights", flightService.findAll());
        return "flights";
    }
}
