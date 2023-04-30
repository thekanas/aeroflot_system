package by.stolybko.web.servlet;

import by.stolybko.database.entity.Person;
import by.stolybko.service.PersonService;
import by.stolybko.web.util.PagesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/add")
public class PersonAddServlet extends HttpServlet {

    private final PersonService personService = PersonService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PagesUtil.ADD).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!requestIsValid(req)) {
            doGet(req, resp);
        }

        Person person = Person.builder()
                .fullName(req.getParameter("inputFullName"))
                .position(req.getParameter("inputPosition"))
                .birthDay(LocalDate.parse(req.getParameter("inputBirthDay"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .description(req.getParameter("inputDescription"))
                .build();

        personService.create(person);

        if (person != null) {
            req.setAttribute("successfullyAdd", true);
        }
        doGet(req, resp);
    }

    private boolean requestIsValid(final HttpServletRequest req) {

        final String fullName = req.getParameter("inputFullName");
        final String position = req.getParameter("inputPosition");
        final String birthDay = req.getParameter("inputBirthDay");

        return fullName != null && fullName.length() > 0 &&
                position != null && position.length() > 0 &&
                birthDay != null && birthDay.length() > 0;
    }
}
