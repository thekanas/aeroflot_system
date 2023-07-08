package by.stolybko.web.servlet;

import by.stolybko.database.entity.PersonEntity;
import by.stolybko.service.PersonService;
import by.stolybko.web.util.PagesUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@WebServlet("/update")
@RequiredArgsConstructor
public class PersonUpdateServlet extends HttpServlet {

    private PersonService personService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");
        personService = ac.getBean(PersonService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            req.setAttribute("person", personService.findById(Long.parseLong(id)));
        } catch (Exception e) {
            req.getRequestDispatcher(PagesUtil.PERSONS).forward(req, resp);
        }
        req.getRequestDispatcher(PagesUtil.UPDATE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!requestIsValid(req)) {
            doGet(req, resp);
        }

        PersonEntity person = PersonEntity.builder()
                .id(Long.parseLong(req.getParameter("id")))
                .fullName(req.getParameter("inputFullName"))
                .position(req.getParameter("inputPosition"))
                .birthDay(LocalDate.parse(req.getParameter("inputBirthDay"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .description(req.getParameter("inputDescription"))
                .build();

        Optional<PersonEntity> personUpadte = personService.update(person);

        if (personUpadte.isPresent()) {
            req.setAttribute("successfullyUpdate", true);
        }
        doGet(req, resp);
    }

    private boolean requestIsValid(final HttpServletRequest req) {

        final String fullName = req.getParameter("inputFullName");
        final String position = req.getParameter("inputPosition");
        final String birthDay = req.getParameter("inputBirthDay");

        return fullName != null && fullName.length() > 0
                && position != null && position.length() > 0
                && birthDay != null && birthDay.length() > 0;
    }
}
