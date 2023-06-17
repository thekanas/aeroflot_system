package by.stolybko.web.servlet;

import by.stolybko.service.FlightService;
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

@WebServlet("/flights")
@RequiredArgsConstructor
public class FlightServlet extends HttpServlet {

    private FlightService flightService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");
        flightService = ac.getBean(FlightService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("flights", flightService.findAll());
        req.getRequestDispatcher(PagesUtil.FLIGHTS).forward(req, resp);
    }
}
