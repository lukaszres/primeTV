package pl.lkre.program.generator.builder;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import pl.lkre.program.tv.model.Seance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeanceBuilder {
    private static final Pattern EPISODE_PATTERN = Pattern.compile("( odc. \\d)(.*)");
    private final static String TIME_PATTERN = "yyyy,MM,dd,HH,mm";
    private final static String LACK_OF_TITLE = "brak tytułu";
    private final static String LACK_OF_GENRE = "brak gatunku";

    public Seance createSeance(
            String channel,
            String date,
            Element seance
    ) throws ParseException {
        Date dateTime = new SimpleDateFormat(TIME_PATTERN).parse(date);
        Optional<Element> genreOptional = Optional.ofNullable(seance.getElementsByClass("st")
                .first());
        String genreString = genreOptional.isPresent() ? genreOptional.get()
                .ownText() : LACK_OF_GENRE;

        Matcher matcher = EPISODE_PATTERN.matcher(genreString);
        String episode = null;
        if (matcher.find()) {
            String episodeString = matcher.group(0);
            genreString = genreString.replace(episodeString, "");
            episode = episodeString;
        }
        genreOptional.ifPresent(Node::remove);
        Optional<Element> titleOptional = Optional.ofNullable(seance.getElementsByClass("sd")
                .first());
        String seanceTitle = titleOptional.isPresent() ? titleOptional.get()
                .ownText() : LACK_OF_TITLE;

        if (titleOptional.isPresent()) {
            Element hrefTitle = titleOptional.get()
                    .select("a[href]")
                    .first();
            if (hrefTitle != null && hrefTitle.hasText()) {
                seanceTitle = hrefTitle.html();
            }
        }
        return new Seance(seanceTitle, dateTime, genreString, episode, channel);
    }
}
