package pl.lkre.program.generator.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pl.lkre.program.tv.model.Channel;
import pl.lkre.program.tv.model.Seance;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ChannelService {
    private final static String TV_GUIDE_URL = "https://www.filmweb.pl/program-tv/";
    private SeanceService seanceService = new SeanceService();
    private GenreService genreService = new GenreService();

    List<Channel> getChannels(List<String> channelNames) {
        List<Channel> channelList = new ArrayList<>();
        channelNames.forEach(
                channel -> {
                    try {
                        channelList.add(getChannel(channel));
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
        );
        return channelList;
    }

    private Channel getChannel(String channel) throws IOException, ParseException {
        String url = TV_GUIDE_URL + channel;
        Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);

        Optional<Element> dayOptional = Optional.ofNullable(document.getElementsByClass("day_0")
                .first());
        List<Seance> seancesList = seanceService.getSeances(getChannelName(channel), dayOptional);
        List<String> genres = genreService.createGenres(seancesList);
        return new Channel(seancesList, genres);
    }

    private String getChannelName(String channel) {

        channel = channel.replace("+", " ");
        channel = channel.replace("%2B", "+");
        channel = channel.replace("%C5%82", "ł");
        return channel;
    }
}

