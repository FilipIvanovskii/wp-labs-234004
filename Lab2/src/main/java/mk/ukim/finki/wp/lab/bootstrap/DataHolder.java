package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Instructor;
import mk.ukim.finki.wp.lab.model.Registration;
import mk.ukim.finki.wp.lab.model.Workshop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Workshop> workshops = null;
    public static List<Registration> registrations = null;
    public static List<Instructor> instructors = null;

    @PostConstruct
    public void init() {
        instructors = new ArrayList<>();
        instructors.add(new Instructor(1L, "Ana", "Petrovska", "ana.petrovska@finki.ukim.mk", "Java & Spring"));
        instructors.add(new Instructor(2L, "Marko", "Nikolov", "marko.nikolov@finki.ukim.mk", "DevOps"));
        instructors.add(new Instructor(3L, "Elena", "Stojanova", "elena.stojanova@finki.ukim.mk", "Design & UX"));

        workshops = new ArrayList<>();
        workshops.add(new Workshop(1L, "Java Basics", "Programming", 1500.0, 20, instructors.get(0)));
        workshops.add(new Workshop(2L, "Spring Boot Essentials", "Programming", 2500.0, 15, instructors.get(0)));
        workshops.add(new Workshop(3L, "Intro to Docker", "DevOps", 2000.0, 10, instructors.get(1)));
        workshops.add(new Workshop(4L, "Kubernetes in Practice", "DevOps", 3500.0, 8, instructors.get(1)));
        workshops.add(new Workshop(5L, "UI/UX Design Fundamentals", "Design", 1800.0, 12, instructors.get(2)));
        workshops.add(new Workshop(6L, "Figma for Beginners", "Design", 1200.0, 25, instructors.get(2)));
        workshops.add(new Workshop(7L, "Machine Learning 101", "Data Science", 4000.0, 5, instructors.get(0)));
        workshops.add(new Workshop(8L, "SQL and Databases", "Data", 1700.0, 18, instructors.get(0)));
        workshops.add(new Workshop(9L, "Cyber Security Basics", "Security", 3000.0, 0, instructors.get(1)));
        workshops.add(new Workshop(10L, "Agile and Scrum", "Management", 1000.0, 30, instructors.get(2)));

        registrations = new ArrayList<>();
    }
}
