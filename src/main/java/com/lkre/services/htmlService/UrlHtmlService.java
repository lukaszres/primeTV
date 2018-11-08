package com.lkre.services.htmlService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ManagedBean
@ViewScoped
public class UrlHtmlService implements HtmlService {

    private final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");

    @Override
    public List<Seance> main() {
        List<String> channelStringList = getSelectedChannels();
        List<Channel> channelList = new ArrayList<>();
        channelStringList.forEach(
                channel -> {
                    try {
                        channelList.add(ChannelFactory.getChannel(channel));
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
        );

        List<String> availableGenres =
                ChannelFactory.getAvailableGenres(channelList)
                              .stream()
                              .sorted()
                              .collect(Collectors.toList());

        List<String> selectedGenres = new ArrayList<>();
//        for (String arg : args) {
//            selectedGenres.add(arg.replace("_", " "));
//        }
//        if (selectedGenres.isEmpty()) {
        selectedGenres.add("horror");
        selectedGenres.add("horror komediowy");
        selectedGenres.add("horror SF");
        selectedGenres.add("thriller");
        selectedGenres.add("thriller SF");
//        }

//        List<Seance> seancesByGenre = ChannelFactory.getSeancesByGenre(channelList,
// selectedGenres);
        List<Seance> allSeances = ChannelFactory.getAllSeances(channelList);
        sortByTime(allSeances);
//         TODO LukRes 2018-11-07: this method should return all seances
//         TODO LukRes 2018-11-07: implement adding to database
        return allSeances;
    }

    private List<String> getSelectedChannels() {
        List<String> listOfChannels = new ArrayList<>();
        for (ChannelLis channel : ChannelLis.values()) {
            listOfChannels.add(channel.getValue());
        }
        return listOfChannels;
    }

    private void showResult(List<Seance> seances) {
        seances.forEach(seance -> {
            Optional<String> episodeOptional = seance.getEpisode();
            String episode = episodeOptional.orElse("");
            System.out.println(
                    DATE_FORMAT.format(seance.getTime()) + " :: "
                            + seance.getChannel() + " :: "
                            + seance.getGenre() + " :: "
                            + seance.getTitle() + episode);
        });
        System.out.println(seances.size());
    }

    private void saveResult(
            List<Seance> seances,
            List<String> availableGenres
    ) throws IOException {
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("program.txt"), StandardCharsets.UTF_8));
        seances.forEach(seance -> {
            Optional<String> episodeOptional = seance.getEpisode();
            String episode = episodeOptional.orElse("");
            try {
                out.write(
                        DATE_FORMAT.format(seance.getTime())
                                + " :: " + seance.getChannel()
                                + " :: " + seance.getGenre()
                                + " :: " + seance.getTitle()
                                + " :: " + episode
                                + "\n"
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        out.write(seances.size());
        out.write(String.valueOf(availableGenres));
        out.close();
    }

    private void sortByTime(List<Seance> seances) {
        seances.sort(Comparator.comparing(Seance::getTime));
    }
}

