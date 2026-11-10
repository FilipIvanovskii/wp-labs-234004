package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lab.model.Registration;
import mk.ukim.finki.wp.lab.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public String register(@RequestParam String workshopTitle,
                           @RequestParam String participantName,
                           @RequestParam String participantEmail,
                           @RequestParam int numSeats,
                           @RequestHeader("User-Agent") String userAgent,
                           HttpServletRequest request,
                           Model model) {
        try {
            Registration registration = registrationService.register(
                    workshopTitle, participantName, participantEmail, numSeats);
            model.addAttribute("registration", registration);
            model.addAttribute("ipAddress", request.getRemoteAddr());
            model.addAttribute("userAgent", userAgent);
            return "registrationConfirmation";
        } catch (IllegalArgumentException e) {
            return "redirect:/workshops?errorMessage="
                    + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
    }
}
