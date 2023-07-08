package by.stolybko.web.controller;

import by.stolybko.database.dto.PersonDTO;
import by.stolybko.database.dto.PersonFilter;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static by.stolybko.web.util.PagesUtil.*;

@Controller
@RequestMapping(PERSONS)
@SessionAttributes("personFilter")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @ModelAttribute("personFilter")
    public PersonFilter personFilter() {
        return new PersonFilter();
    }

    @GetMapping
    public String showPersons(@RequestParam(value = "page", required = false) Integer page, @ModelAttribute("personFilter") PersonFilter personFilter,  Model model) {

        if (page == null) {
            page = 1;
        }
        List<PersonEntity> persons = personService.findByFilter(personFilter, page);
        System.out.println();
        long countRecords = personService.countAllRecordsByFilter(personFilter);
        int countPages;
        if (personFilter.getLimit() == null || personFilter.getLimit().isEmpty()) {
            countPages = (int) Math.ceil(countRecords * 1.0 / (persons.size()));
        } else {
            countPages = (int) Math.ceil(countRecords * 1.0 / (Integer.parseInt(personFilter.getLimit())));
        }

        List<String> positions = personService.getAllPosition();

        model.addAttribute("persons", persons);
        model.addAttribute("countPages", countPages);
        model.addAttribute("page", page);
        model.addAttribute("positions", positions);
        System.out.println();
        return "persons";
    }

    @PostMapping
    public String showPersonsByFilter(@ModelAttribute("personFilter") PersonFilter personFilter, Model model) {

        model.addAttribute("personFilter", personFilter);
        return "redirect:/persons";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("person", personService.findById(id));
            return "person";
        } catch (Exception e) {
            return "persons";
        }
    }

    @GetMapping("/add")
    public String showAddPerson(@ModelAttribute("person") PersonDTO person) {
            return "personAdd";
    }

    @PostMapping("/add")
    public String addPerson(@ModelAttribute("person") PersonDTO person, Model model) {
        if (!personIsValid(person)) {
            model.addAttribute("errorAdd", true);
            return "personAdd";
        }

        PersonEntity personEntity = personService.save(person);
        if (personEntity != null) {
            model.addAttribute("successfullyAdd", true);
        }
        return "personAdd";
    }

    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") Long id, Model model) {
        personService.delete(id);
        return "redirect:/persons";
    }
    @GetMapping("/{id}/update")
    public String showPersonUpdate(@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("person", personService.findById(id));
            return "personUpdate";
        } catch (Exception e) {
            return "persons";
        }
    }

    @PostMapping("/{id}/update")
    public String updatePerson(@ModelAttribute("person") PersonDTO person, @PathVariable("id") Long id) {
        Optional<PersonEntity> personEntity = personService.update(person, id);
        if (personEntity.isEmpty()) {
            return "redirect:/persons/{id}/update?error";
        }

        return "redirect:/persons/{id}/update?successfullyUpdate";
    }

    private boolean personIsValid(PersonDTO person) {

        final String fullName = person.getFullName();
        final String position = person.getPosition();
        final LocalDate birthDay = person.getBirthDay();

        return fullName != null && fullName.length() > 0
                && position != null && position.length() > 0
                && birthDay != null;
    }
}
