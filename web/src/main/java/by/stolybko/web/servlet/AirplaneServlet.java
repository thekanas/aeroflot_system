package by.stolybko.web.servlet;

import by.stolybko.service.AirplaneService;
import by.stolybko.web.util.PagesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/airplanes")
public class AirplaneServlet extends HttpServlet {
    private final AirplaneService airplaneService = AirplaneService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("airplanes", airplaneService.findAll());
        req.getRequestDispatcher(PagesUtil.AIRPLANES).forward(req, resp);



    }
}
