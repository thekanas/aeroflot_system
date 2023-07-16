package by.stolybko.web.controller;


import by.stolybko.database.dto.AirplaneDTO;
import by.stolybko.database.dto.PersonDTO;
import by.stolybko.database.entity.AirplaneEntity;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.service.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLDataException;
import java.util.Optional;

import static by.stolybko.web.util.PagesUtil.*;

@Controller
@RequestMapping(AIRPLANES)
public class AirplaneController {

    private final AirplaneService airplaneService;

    @Autowired
    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @GetMapping
    public String showAirplanes(Model model) {
        model.addAttribute("airplanes", airplaneService.findAll());
        return "airplanes";
    }

    @GetMapping("/add")
    public String showAddAirplane(@ModelAttribute("airplane") AirplaneDTO airplane) {
        return "airplaneAdd";
    }

    @PostMapping("/add")
    public String addAirplane(@ModelAttribute("airplane") AirplaneDTO airplane, Model model) {

        AirplaneEntity airplaneEntity = airplaneService.save(airplane);

        if (airplaneEntity != null) {
            model.addAttribute("successfullyAdd", true);
        }
        else {
            model.addAttribute("errorAdd", true);
        }
        return "airplaneAdd";
    }

    @GetMapping("/{id}")
    public String showAirplane(@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("airplane", airplaneService.findById(id));
            return "airplaneUpdate";
        } catch (Exception e) {
            return "airplanes";
        }
    }

    @PostMapping("/{id}/update")
    public String updateAirplane(@ModelAttribute("airplane") AirplaneDTO airplane, @PathVariable("id") Long id) {
        Optional<AirplaneEntity> airplaneEntity = airplaneService.update(airplane, id);
        if (airplaneEntity.isEmpty()) {
            return "redirect:/airplanes/{id}?error";
        }

        return "redirect:/airplanes/{id}?successfullyUpdate";
    }

    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") Long id, Model model) {

        try {
            airplaneService.delete(id);
            return "redirect:/airplanes";
        } catch (SQLDataException e) {
            return "redirect:/airplanes?errorDelete";
        }
    }
}
