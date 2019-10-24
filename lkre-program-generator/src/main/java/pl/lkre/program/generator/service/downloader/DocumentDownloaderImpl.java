package pl.lkre.program.generator.service.downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

public class DocumentDownloaderImpl implements DocumentDownloader {
    private final static String TV_GUIDE_URL = "https://www.filmweb.pl/program-tv/";

    @Override
    public Document download(String channel) throws IOException {
        String url = TV_GUIDE_URL + channel;
        return Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
    }
}
