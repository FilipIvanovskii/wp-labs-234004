package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.service.WorkshopService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "workshop-list-servlet", urlPatterns = "")
public class WorkshopListServlet extends HttpServlet {

    private final WorkshopService workshopService;
    private final SpringTemplateEngine springTemplateEngine;

    public WorkshopListServlet(WorkshopService workshopService,
                               SpringTemplateEngine springTemplateEngine) {
        this.workshopService = workshopService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        String text = emptyToNull(req.getParameter("text"));
        String maxPriceParam = emptyToNull(req.getParameter("maxPrice"));
        Double maxPrice = maxPriceParam == null ? null : Double.valueOf(maxPriceParam);

        List<Workshop> workshops = (text != null || maxPrice != null)
                ? workshopService.search(text, maxPrice)
                : workshopService.listAll();

        context.setVariable("workshops", workshops);
        context.setVariable("errorMessage", req.getParameter("errorMessage"));

        resp.setContentType("text/html;charset=UTF-8");
        springTemplateEngine.process("listWorkshops.html", context, resp.getWriter());
    }

    private static String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}
