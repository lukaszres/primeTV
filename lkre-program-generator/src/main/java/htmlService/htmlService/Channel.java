package htmlService.htmlService;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
class Channel {

    private List<Seance> seances;
    private List<String> genres;

    Channel(List<Seance> seances) {
        this.seances = seances;
        createGenres();
    }

    private void createGenres() {
        this.genres = new ArrayList<>();
        seances.forEach(seance -> {
                    if (!genres.contains(seance.getGenre()))
                        this.genres.add(seance.getGenre());
                }
        );
    }
}

