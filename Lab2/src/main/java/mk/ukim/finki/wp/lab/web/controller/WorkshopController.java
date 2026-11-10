package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.service.InstructorService;
import mk.ukim.finki.wp.lab.service.WorkshopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class WorkshopController {

    private final WorkshopService workshopService;
    private final InstructorService instructorService;

    public WorkshopController(WorkshopService workshopService,
                              InstructorService instructorService) {
        this.workshopService = workshopService;
        this.instructorService = instructorService;
    }

    @GetMapping("/")
    public String redirectToWorkshops() {
        return "redirect:/workshops";
    }

    @GetMapping("/workshops")
    public String getWorkshopsPage(@RequestParam(required = false) String text,
                                   @RequestParam(required = false) Double maxPrice,
                                   @RequestParam(required = false) String errorMessage,
                                   Model model) {
        String searchText = (text == null || text.isBlank()) ? null : text;
        List<Workshop> workshops = (searchText != null || maxPrice != null)
                ? workshopService.search(searchText, maxPrice)
                : workshopService.listAll();

        model.addAttribute("workshops", workshops);
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "listWorkshops";
    }

    @PostMapping("/workshops/add")
    public String saveWorkshop(@RequestParam String title,
                               @RequestParam String category,
                               @RequestParam Double price,
                               @RequestParam Integer capacity,
                               @RequestParam Long instructorId) {
        try {
            workshopService.create(title, category, price, capacity, instructorId);
            return "redirect:/workshops";
        } catch (IllegalArgumentException e) {
            return "redirect:/workshops?errorMessage="
                    + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
    }

    @PostMapping("/workshops/edit/{workshopId}")
    public String editWorkshop(@PathVariable Long workshopId,
                               @RequestParam String title,
                               @RequestParam String category,
                               @RequestParam Double price,
                               @RequestParam Integer capacity,
                               @RequestParam Long instructorId) {
        try {
            workshopService.update(workshopId, title, category, price, capacity, instructorId);
            return "redirect:/workshops";
        } catch (IllegalArgumentException e) {
            return "redirect:/workshops?errorMessage="
                    + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
    }

    @PostMapping("/workshops/delete/{id}")
    public String deleteWorkshop(@PathVariable Long id) {
        workshopService.deleteById(id);
        return "redirect:/workshops";
    }

    @GetMapping("/workshops/workshop-form/{id}")
    public String getEditWorkshopForm(@PathVariable Long id, Model model) {
        return workshopService.findById(id)
                .map(workshop -> {
                    model.addAttribute("workshop", workshop);
                    model.addAttribute("instructors", instructorService.findAll());
                    return "workshop-form";
                })
                .orElse("redirect:/workshops?errorMessage="
                        + URLEncoder.encode("WorkshopNotFound", StandardCharsets.UTF_8));
    }

    @GetMapping("/workshops/workshop-form")
    public String getAddWorkshopPage(Model model) {
        model.addAttribute("instructors", instructorService.findAll());
        return "workshop-form";
    }
}
