package by.stolybko.web.servlet;

import by.stolybko.database.dto.PersonFilter;
import by.stolybko.database.entity.Person;
import by.stolybko.service.PersonService;
import by.stolybko.web.util.PagesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/persons")
public class PersonServlet extends HttpServlet {

    private final PersonService personService = PersonService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String id = req.getParameter("id");
        Integer page = Integer.parseInt(req.getParameter("page") == null ? "1" : req.getParameter("page"));
        if (id == null) {

            PersonFilter personFilter = (PersonFilter) session.getAttribute("personFilter");
            if (personFilter == null) {
                personFilter = new PersonFilter();
            }

            List<Person> persons = personService.findByFilter(personFilter, page);
            System.out.println();
            int countRecords = personService.countAllRecordsByFilter(personFilter);
            int countPages;
            if (personFilter.getLimit() == null || personFilter.getLimit().isEmpty()) {
                countPages = (int) Math.ceil(countRecords * 1.0 / (persons.size()));
            } else {
                countPages = (int) Math.ceil(countRecords * 1.0 / (Integer.parseInt(personFilter.getLimit())));
            }

            List<String> positions = personService.getAllPosition();

            req.setAttribute("persons", persons);
            req.setAttribute("countPages", countPages);
            req.setAttribute("page", page);
            req.setAttribute("positions", positions);

            session.setAttribute("personFilter", personFilter);
            req.getRequestDispatcher(PagesUtil.PERSONS).forward(req, resp);
        } else {
            req.setAttribute("person", personService.findById(Long.parseLong(id)));
            req.getRequestDispatcher(PagesUtil.PERSON).forward(req, resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PersonFilter personFilter = new PersonFilter(
                req.getParameter("fullName"),
                req.getParameter("position"),
                req.getParameter("birthDayFrom"),
                req.getParameter("birthDayTO"),
                req.getParameter("limit")
        );

        HttpSession session = req.getSession();
        session.setAttribute("personFilter", personFilter);

        doGet(req, resp);
    }


}
