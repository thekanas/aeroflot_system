package by.stolybko.web.controller;


import by.stolybko.service.AirplaneService;
import by.stolybko.web.util.PagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
