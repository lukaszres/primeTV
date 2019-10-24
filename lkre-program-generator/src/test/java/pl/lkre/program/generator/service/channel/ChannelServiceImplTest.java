package pl.lkre.program.generator.service.channel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.lkre.program.generator.service.downloader.DocumentDownloader;
import pl.lkre.program.tv.model.Channel;
import pl.lkre.program.tv.model.Seance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelServiceImplTest {

    private static DocumentDownloader downloader;

    @BeforeAll
    static void setUp() {
        downloader = new DocumentDownloader() {
            @Override
            public Document download(String channel) throws IOException {
                String file = Objects.requireNonNull(getClass().getClassLoader().getResource("TVP+1.html")).getFile();
                String htmlString = new String(Files.readAllBytes(Paths.get(file)));
                return Jsoup.parse(htmlString);
            }
        };
    }

    @Test
    void getChannel_TVP1HtmlFile_newChannel() throws IOException, ParseException {
        //given
        ChannelService channelService = new ChannelServiceImpl(downloader);
        //when
        Channel actual = channelService.getChannel("channelName");
        Seance sportSeance = new Seance("Sport", new Date(1571937900000L), "program sportowy", null, "channelName");
        //then
        assertThat(actual.getSeances()).hasSize(42);
        assertThat(actual.getGenres()).hasSize(16);
        assertThat(actual.getSeances())
                .filteredOn(o -> o.getGenre().equals("program sportowy"))
                .containsExactly(sportSeance);
        assertThat(actual.getSeances())
                .filteredOn(o -> o.getGenre().equals("program informacyjny")).hasSize(10);
    }
}