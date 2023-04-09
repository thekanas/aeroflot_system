package by.stolybko.web.servlet;

import by.stolybko.service.PersonService;
import by.stolybko.web.util.PagesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/persons")
public class PersonServlet extends HttpServlet {

    private final PersonService personService = PersonService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("persons", personService.getAll());
        System.out.println();
        req.getRequestDispatcher(PagesUtil.BOOKS).forward(req, resp);



    }


}
