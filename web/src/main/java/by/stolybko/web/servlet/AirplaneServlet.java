package by.stolybko.web.servlet;

import by.stolybko.service.AirplaneService;
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

@WebServlet("/airplanes1")
@RequiredArgsConstructor
public class AirplaneServlet extends HttpServlet {

    private AirplaneService airplaneService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");
        airplaneService = ac.getBean(AirplaneService.class);
    }
   @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("airplanes", airplaneService.findAll());
        req.getRequestDispatcher(PagesUtil.AIRPLANES).forward(req, resp);
    }
}
