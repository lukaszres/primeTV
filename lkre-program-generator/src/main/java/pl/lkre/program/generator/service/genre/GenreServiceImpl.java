package pl.lkre.program.generator.service.genre;

import pl.lkre.program.tv.model.Seance;

import java.util.ArrayList;
import java.util.List;

public class GenreServiceImpl implements GenreService {

    public List<String> createGenres(List<Seance> seances) {
        List<String> genres = new ArrayList<>();
        seances.forEach(seance -> {
                    if (!genres.contains(seance.getGenre()))
                        genres.add(seance.getGenre());
                }
        );
        return genres;
    }
}
