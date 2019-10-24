package pl.lkre.program.generator.service.genre;

import pl.lkre.program.tv.model.Seance;

import java.util.List;

public interface GenreService {
    List<String> createGenres(List<Seance> seances);
}
