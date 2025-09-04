package pl.lkre.program.generator.builder;

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.lkre.program.tv.model.Seance;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class SeanceBuilder {
        private final static String LACK_OF_TITLE = "brak tytułu";
        private final static String LACK_OF_GENRE = "brak gatunku";

        public Seance createSeance(
                        String channel,
                        String date,
                        Element seance) throws ParseException {

                LocalDateTime ldt = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                Date dateTime = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

                Elements genreElements = seance.getElementsByClass("tvPageGroupedSeances__genre");
                String genreString = genreElements.size() > 1 ? genreElements.get(1).ownText()
                                : genreElements.get(0).ownText();
                if (Objects.isNull(genreString)) {
                        genreString = LACK_OF_GENRE;
                }

                StringBuilder episoBuilder = new StringBuilder();
                Elements episodes = seance.select("strong[data-source-sub-title]");
                episodes.forEach(s -> episoBuilder.append(" " + s.text()));
                String episode = episoBuilder.toString();

                String seanceTitle = seance.getElementsByClass("tvPageGroupedSeances__headerTitle").text();
                if (StringUtil.isBlank(seanceTitle)) {
                        seanceTitle = seance.getElementsByClass("tvPageGroupedSeances__title").text();
                        if (StringUtil.isBlank(seanceTitle)) {
                                seanceTitle = LACK_OF_TITLE;
                        }
                }
                return new Seance(seanceTitle, dateTime, genreString, episode, channel);
        }
}
