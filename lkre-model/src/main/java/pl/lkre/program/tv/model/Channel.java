package pl.lkre.program.tv.model;

import lombok.Value;

import java.util.List;

@Value
public class Channel {

    private List<Seance> seances;
    private List<String> genres;

}

