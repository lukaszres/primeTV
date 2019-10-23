package pl.lkre.program.generator.model;

import lombok.Value;

import java.util.List;

@Value
public class Channel {

    private List<Seance> seances;
    private List<String> genres;

}

