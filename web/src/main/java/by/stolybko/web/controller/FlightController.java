package by.stolybko.web.controller;


import by.stolybko.database.dto.CrewDTO;
import by.stolybko.database.dto.FlightDTO;
import by.stolybko.database.dto.FlightShowDTO;
import by.stolybko.database.dto.PersonDetails;
import by.stolybko.database.entity.AirportEntity;
import by.stolybko.database.entity.FlightEntity;
import by.stolybko.service.AirportService;
import by.stolybko.service.FlightService;
import by.stolybko.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLDataException;
import java.util.List;
import static by.stolybko.web.util.PagesUtil.FLIGHTS;

@Controller
@RequestMapping(FLIGHTS)
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;
    private final PersonService personService;
    private final AirportService airportService;

    @GetMapping
    public String showFlights(Model model) {
        List<FlightShowDTO> flights = flightService.findAll();
        model.addAttribute("flights", flights);
        return "flights";
    }
    @GetMapping("/current")
    public String showCurrentFlights(Model model) {
        List<FlightShowDTO> flights = flightService.findCurrentFlightsWithWeather();
        model.addAttribute("flights", flights);
        model.addAttribute("time", 0);
        return "flightsCurrent";
    }

    @GetMapping("/archived")
    public String showArchivedFlights(Model model) {
        List<FlightShowDTO> flights = flightService.findArchived();
        model.addAttribute("flights", flights);
        return "flightsArchived";
    }
    @GetMapping("/assigned")
    public String showAssignedFlights(Model model) {
        List<FlightShowDTO> flights = flightService.findAssigned();
        model.addAttribute("flights", flights);
        return "flightsAssigned";
    }
    @GetMapping("/my")
    public String showMyFlights(Authentication authentication, Model model) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        List<FlightEntity> flights = personDetails.getPerson().getFlights();
        model.addAttribute("flights", flights);

        return "flightsMy";
    }

    @GetMapping("/add")
    public String showAddFlight(@ModelAttribute("flight") FlightDTO flight, @ModelAttribute("airport") AirportEntity airport, Model model) {
        model.addAttribute("airports", flightService.findAllAirports());
        model.addAttribute("airplanes", flightService.findAllAirplanes());
        return "flightAddS1";
    }

    @PostMapping("/add")
    public String addFlight(@ModelAttribute("flight") FlightDTO flight,  @ModelAttribute("airport") AirportEntity airport, Model model) {
        FlightEntity flightEntity = flightService.save(flight);

        if (flightEntity != null) {
            return "redirect:/flights/add/step2/" + flightEntity.getId();
        } else {
            model.addAttribute("errorAdd", true);
        }
        return "flightAddS1";
    }
    @GetMapping("/add/step2/{id}")
    public String showAddFlightStep2(@PathVariable("id") Long id, @ModelAttribute("crew") CrewDTO crew, Model model) {
        model.addAttribute("flight", flightService.findById(id));
        model.addAttribute("pilots", personService.findPilots());
        model.addAttribute("stewards", personService.findStewards());
        return "flightAddS2";
    }
    @PostMapping("/add/step2/{id}")
    public String addFlightStep2(@PathVariable("id") Long id, @ModelAttribute("crew") CrewDTO crew, Model model) {

        flightService.flightAddCrew(id, crew);

        return "redirect:/flights";
    }

    @PostMapping("/add/airport")
    public String addAirport(@ModelAttribute("airport") AirportEntity airport, Model model) {
        AirportEntity airportSave = airportService.save(airport);

        if (airportSave != null) {
            return "redirect:/flights/add?airportSuccess";

        } else {
            model.addAttribute("errorAdd", true);
        }
            return "redirect:/flights/add?airportError";
    }

    @GetMapping("/update/{id}")
    public String showUpdateFlight(@PathVariable("id") Long id, @ModelAttribute("flightDTO") FlightDTO flightDTO, @ModelAttribute("crew") CrewDTO crew, Model model) {
        FlightEntity flight = flightService.findById(id);
        model.addAttribute("flight", flight);
        model.addAttribute("pilots", personService.findPilots());
        model.addAttribute("stewards", personService.findStewards());
        model.addAttribute("airports", flightService.findAllAirports());
        model.addAttribute("airplanes", flightService.findAllAirplanes());
        return "flightUpdate";
    }

    @PostMapping("/update/{id}/flight")
    public String updateFlight(@PathVariable("id") Long id, @ModelAttribute("flightDTO") FlightDTO flightDTO, Model model) {
        FlightEntity flightEntity = flightService.update(flightDTO, id);

        if (flightEntity != null) {
            return "redirect:/flights/update/" + id + "?successfullyUpdate";
        } else {
            return "redirect:/flights/update/" + id + "?errorUpdate";
        }
    }

    @PostMapping("/update/{id}/aircrew")
    public String updateAircrew(@PathVariable("id") Long id, @ModelAttribute("crew") CrewDTO crew, Model model) {
        FlightEntity flightEntity = flightService.flightUpdateCrew(crew, id);

        if (flightEntity != null) {
            return "redirect:/flights/update/" + id + "?successfullyUpdateCrew";
        } else {
            return "redirect:/flights/update/" + id + "?errorUpdateCrew";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteFlight(@PathVariable("id") Long id, Model model) {

        try {
            flightService.delete(id);
            return "redirect:/flights";
        } catch (SQLDataException e) {
            return "redirect:/flights?errorDelete";
        }
    }

    @GetMapping("/update/{id}/inreserve")
    public String updateInReserve(@PathVariable("id") Long id) {
        flightService.inReserve(id);

        return "redirect:/flights/current";
    }

    @PostMapping("/update/{id}/timePlus")
    public String updateTimePlus(@PathVariable("id") Long id, @ModelAttribute("time") Integer time) {
        flightService.timePlus(id, time);

        return "redirect:/flights/current";
    }

    @PostMapping("/update/{id}/timeMinus")
    public String updateTimeMinus(@PathVariable("id") Long id, @ModelAttribute("time") Integer time) {
        flightService.timeMinus(id, time);

        return "redirect:/flights/current";
    }
}
