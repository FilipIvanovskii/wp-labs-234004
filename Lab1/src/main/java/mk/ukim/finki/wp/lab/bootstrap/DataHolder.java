package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Registration;
import mk.ukim.finki.wp.lab.model.Workshop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Workshop> workshops = null;
    public static List<Registration> registrations = null;

    @PostConstruct
    public void init() {
        workshops = new ArrayList<>();
        workshops.add(new Workshop("Java Basics", "Programming", 1500.0, 20));
        workshops.add(new Workshop("Spring Boot Essentials", "Programming", 2500.0, 15));
        workshops.add(new Workshop("Intro to Docker", "DevOps", 2000.0, 10));
        workshops.add(new Workshop("Kubernetes in Practice", "DevOps", 3500.0, 8));
        workshops.add(new Workshop("UI/UX Design Fundamentals", "Design", 1800.0, 12));
        workshops.add(new Workshop("Figma for Beginners", "Design", 1200.0, 25));
        workshops.add(new Workshop("Machine Learning 101", "Data Science", 4000.0, 5));
        workshops.add(new Workshop("SQL and Databases", "Data", 1700.0, 18));
        workshops.add(new Workshop("Cyber Security Basics", "Security", 3000.0, 0));
        workshops.add(new Workshop("Agile and Scrum", "Management", 1000.0, 30));

        registrations = new ArrayList<>();
    }
}
