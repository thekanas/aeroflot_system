package by.stolybko.web.servlet;

import by.stolybko.config.ServiceConfig;
import by.stolybko.service.PersonService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.IOException;

@WebServlet("/delete")
@RequiredArgsConstructor
public class PersonDeleteServlet extends HttpServlet {

    private PersonService personService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ApplicationContext ac = new AnnotationConfigApplicationContext(ServiceConfig.class);

        config.getServletContext().setAttribute("applicationContext", ac);
        personService = ac.getBean(PersonService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        if (idIsNumber(req)) {
            personService.delete(Long.parseLong(req.getParameter("id")));
            resp.sendRedirect("/persons");
        }

    }

    public boolean idIsNumber(HttpServletRequest request) {
        final String id = request.getParameter("id");
        return id != null
                && (id.length() > 0)
                && id.matches("[+]?\\d+");
    }
}
