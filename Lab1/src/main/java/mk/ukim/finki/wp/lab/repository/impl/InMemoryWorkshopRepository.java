package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.repository.WorkshopRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryWorkshopRepository implements WorkshopRepository {

    @Override
    public List<Workshop> findAll() {
        return DataHolder.workshops;
    }

    @Override
    public Optional<Workshop> findByTitle(String title) {
        return DataHolder.workshops.stream()
                .filter(w -> w.getTitle().equals(title))
                .findFirst();
    }

    @Override
    public List<Workshop> search(String text, Double maxPrice) {
        String needle = (text == null || text.isBlank()) ? null : text.toLowerCase();
        return DataHolder.workshops.stream()
                .filter(w -> needle == null
                        || w.getTitle().toLowerCase().contains(needle)
                        || w.getCategory().toLowerCase().contains(needle))
                .filter(w -> maxPrice == null || w.getPrice() <= maxPrice)
                .toList();
    }
}
