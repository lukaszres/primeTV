package pl.lkre.program.generator.service;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.lkre.program.generator.builder.SeanceBuilder;
import pl.lkre.program.tv.model.Channel;
import pl.lkre.program.tv.model.Seance;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class SeanceService {
    private final static String LACK_OF_TIME = "0000,00,00,00,00";

    private SeanceBuilder seanceBuilder = new SeanceBuilder();

    List<Seance> getAllSeances(List<Channel> channels) {
        List<Seance> seances = new ArrayList<>();
        channels.forEach(channel -> seances.addAll(channel.getSeances()));
        return seances;
    }

    List<Seance> getSeances(String channel, Optional<Element> dayOptional) throws ParseException {
        List<Seance> seancesList = new ArrayList<>();
        if (dayOptional.isPresent() && dayOptional.get()
                .hasText()) {
            Elements seance = dayOptional.get()
                    .getElementsByClass("seance");
            Optional<Elements> seancesOptional = Optional.ofNullable(seance);
            if (seancesOptional.isPresent()) {
                for (Element s : seancesOptional.get()) {
                    String dateStart = s.hasText() ? s.attr("data-start") : LACK_OF_TIME;
                    seancesList.add(seanceBuilder.createSeance(channel, dateStart, s));
                }
            }
        }
        return seancesList;
    }

}
