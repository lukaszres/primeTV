package com.lkre.services.htmlService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ChannelFactory {

    private static final Pattern EPISODE_PATTERN = Pattern.compile("( odc. \\d)(.*)");
    private final static String TIME_PATTERN = "yyyy,MM,dd,HH,mm";
    private final static String LACK_OF_TIME = "0000,00,00,00,00";
    private final static String LACK_OF_TITLE = "brak tytułu";
    private final static String LACK_OF_GENRE = "brak gatunku";

    static Channel getChannel(String channel) throws IOException, ParseException {
        String url = "https://www.filmweb.pl/program-tv/" + channel;

        Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);

        Optional<Element> dayOptional = Optional.ofNullable(document.getElementsByClass("day_0").first());
        List<Seance> seancesList = new ArrayList<>();
        if (dayOptional.isPresent() && dayOptional.get().hasText()) {
            Elements seance = dayOptional.get().getElementsByClass("seance");
            Optional<Elements> seancesOptional = Optional.ofNullable(seance);
            if (seancesOptional.isPresent()) {
                for (Element s : seancesOptional.get()) {
                    String dateStart = s.hasText() ? s.attr("data-start") : LACK_OF_TIME;
                    seancesList.add(createSeance(getChannelName(channel), dateStart, s));
                }
            }
        }
        return new Channel(seancesList);
    }

    private static Seance createSeance(String channel, String date, Element seance) throws ParseException {
        Date dateTime = new SimpleDateFormat(TIME_PATTERN).parse(date);
        Optional<Element> genreOptional = Optional.ofNullable(seance.getElementsByClass("st").first());
        String genreString = genreOptional.isPresent() ? genreOptional.get().ownText() : LACK_OF_GENRE;

        Matcher matcher = EPISODE_PATTERN.matcher(genreString);
        String episode = null;
        if (matcher.find()) {
            String episodeString = matcher.group(0);
            genreString = genreString.replace(episodeString, "");
            episode = episodeString;
        }
        genreOptional.ifPresent(Node::remove);
        Optional<Element> titleOptional = Optional.ofNullable(seance.getElementsByClass("sd").first());
        String seanceTitle = titleOptional.isPresent() ? titleOptional.get().ownText() : LACK_OF_TITLE;

        if (titleOptional.isPresent()) {
            Element hrefTitle = titleOptional.get().select("a[href]").first();
            if (hrefTitle != null && hrefTitle.hasText()) {
                seanceTitle = hrefTitle.html();
            }
        }
        return new Seance(seanceTitle, dateTime, genreString, episode, channel);
    }

    static List<String> getAvailableGenres(List<Channel> channels) {
        List<String> availableGenres = new ArrayList<>();

        channels.forEach(channel ->
                channel.getGenres().forEach(genre -> {
                    if (!availableGenres.contains(genre)) {
                        availableGenres.add(genre);
                    }
                })
        );
        return availableGenres;
    }

    static List<Seance> getSeancesByGenre(List<Channel> channels, List<String> selectedGenres) {
        List<Seance> seances = new ArrayList<>();

        selectedGenres.forEach(genre ->
                channels.forEach(channel -> {
                    if (channel.getGenres().contains(genre)) {
                        channel.getSeances().forEach(seance -> {
                            if (seance.getGenre().equals(genre))
                                seances.add(seance);
                        });
                    }
                })
        );
        return seances;
    }

    private static String getChannelName(String channel) {

        channel = channel.replace("+", " ");
        channel = channel.replace("%2B", "+");
        channel = channel.replace("%C5%82", "ł");
        return channel;
    }
}

