package pl.lkre.program.generator.service.channel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pl.lkre.program.generator.service.GenreService;
import pl.lkre.program.generator.service.SeanceService;
import pl.lkre.program.generator.service.downloader.DocumentDownloader;
import pl.lkre.program.generator.service.downloader.DocumentDownloaderImpl;
import pl.lkre.program.tv.model.Channel;
import pl.lkre.program.tv.model.Seance;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public class ChannelServiceImpl implements ChannelService {
    private DocumentDownloader downloader;
    private SeanceService seanceService = new SeanceService();
    private GenreService genreService = new GenreService();

    ChannelServiceImpl() {
        this.downloader = new DocumentDownloaderImpl();
    }

    @Override
    public Channel getChannel(String channel) throws IOException, ParseException {
        Document document = downloader.download(channel);
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
