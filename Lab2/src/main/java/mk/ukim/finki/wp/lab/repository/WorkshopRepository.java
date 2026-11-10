package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Workshop;

import java.util.List;
import java.util.Optional;

public interface WorkshopRepository {
    List<Workshop> findAll();

    Optional<Workshop> findByTitle(String title);

    Optional<Workshop> findById(Long id);

    List<Workshop> search(String text, Double maxPrice);

    Workshop save(Workshop workshop);

    void deleteById(Long id);
}
