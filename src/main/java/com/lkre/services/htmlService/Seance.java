package com.lkre.services.htmlService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class Seance {

    private String title;
    private Date time;
    private String genre;
    private String episode;
    private String channel;

    Optional<String> getEpisode() {
        return Optional.ofNullable(episode);
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        episode = episode == null ? "" : " :: " + episode;
        return df.format(time) + " :: " + channel + " :: " + genre + " :: " + title + episode;
    }
}

