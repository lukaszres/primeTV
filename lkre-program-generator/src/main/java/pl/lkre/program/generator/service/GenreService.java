package pl.lkre.program.generator.service;

import pl.lkre.program.generator.model.Seance;

import java.util.ArrayList;
import java.util.List;

class GenreService {

    List<String> createGenres(List<Seance> seances) {
        List<String> genres = new ArrayList<>();
        seances.forEach(seance -> {
                    if (!genres.contains(seance.getGenre()))
                        genres.add(seance.getGenre());
                }
        );
        return genres;
    }
}
