package pl.lkre.program.generator.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.lkre.program.generator.service.genre.GenreServiceImpl;
import pl.lkre.program.tv.model.Seance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GenreServiceImplTest {
    private static Seance seanceDetective;
    private static Seance seanceComedy;
    private static final String GENRE_DETECTIVE = "kryminał";
    private static final String GENRE_COMEDY = "komedia";

    @BeforeAll
    static void setUp() {
        seanceDetective = new Seance("Title1", new Date(), List.of(GENRE_DETECTIVE), null, "TV1");
        seanceComedy = new Seance("Title1", new Date(), List.of(GENRE_COMEDY), "3", "TV1");
    }

    @Test
    void createGenres_when_twoDifferentGenres_then_twoGenres_expected() {
        //given
        GenreServiceImpl genreServiceImpl = new GenreServiceImpl();
        List<Seance> seances = new ArrayList<>();
        seances.add(seanceDetective);
        seances.add(seanceComedy);
        //when
        List<String> actual = genreServiceImpl.createGenres(seances);
        //then
        assertThat(actual).containsOnly(GENRE_COMEDY, GENRE_DETECTIVE);
    }

    @Test
    void createGenres_when_twoTheSameGenres_then_oneGenre_expected() {
        //given
        GenreServiceImpl genreServiceImpl = new GenreServiceImpl();
        List<Seance> seances = new ArrayList<>();
        seances.add(seanceDetective);
        seances.add(seanceDetective);
        //when
        List<String> actual = genreServiceImpl.createGenres(seances);
        //then
        assertThat(actual).containsOnly(GENRE_DETECTIVE);
    }

    @Test
    void createGenres_when_oneDifferentAndtwoTheSameGenres_then_twoGenres_expected() {
        //given
        GenreServiceImpl genreServiceImpl = new GenreServiceImpl();
        List<Seance> seances = new ArrayList<>();
        seances.add(seanceDetective);
        seances.add(seanceDetective);
        seances.add(seanceComedy);
        //when
        List<String> actual = genreServiceImpl.createGenres(seances);
        //then
        assertThat(actual).containsOnly(GENRE_COMEDY, GENRE_DETECTIVE);
    }
}