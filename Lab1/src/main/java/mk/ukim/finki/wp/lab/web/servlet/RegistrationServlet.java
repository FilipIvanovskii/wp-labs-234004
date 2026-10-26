package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Registration;
import mk.ukim.finki.wp.lab.service.RegistrationService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "registration-servlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {

    private final RegistrationService registrationService;
    private final SpringTemplateEngine springTemplateEngine;

    public RegistrationServlet(RegistrationService registrationService,
                               SpringTemplateEngine springTemplateEngine) {
        this.registrationService = registrationService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String workshopTitle = req.getParameter("workshopTitle");
        String participantName = req.getParameter("participantName");
        String participantEmail = req.getParameter("participantEmail");
        int numSeats = Integer.parseInt(req.getParameter("numSeats"));

        Registration registration;
        try {
            registration = registrationService.register(
                    workshopTitle, participantName, participantEmail, numSeats);
        } catch (IllegalArgumentException e) {
            resp.sendRedirect("/?errorMessage="
                    + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
            return;
        }

        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);
        context.setVariable("registration", registration);
        context.setVariable("ipAddress", req.getRemoteAddr());
        context.setVariable("userAgent", req.getHeader("User-Agent"));

        resp.setContentType("text/html;charset=UTF-8");
        springTemplateEngine.process("registrationConfirmation.html", context, resp.getWriter());
    }
}
