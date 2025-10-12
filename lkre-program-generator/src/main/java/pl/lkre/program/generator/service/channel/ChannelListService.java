package pl.lkre.program.generator.service.channel;

import pl.lkre.program.generator.service.downloader.DocumentDownloaderImpl;
import pl.lkre.program.tv.model.Channel;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ChannelListService {
    private ChannelService channelService = new ChannelServiceImpl(new DocumentDownloaderImpl());

    public List<Channel> getChannels(List<String> channelNames) {
        List<Channel> channelList = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        channelNames.forEach(
                channel -> {
                    try {
                        channelList.add(channelService.getChannel(channel));
                    } catch (IOException | ParseException e) {
                        errors.add(channel + " : " + e.getMessage());
                    }
                }
        );
        System.out.println("==============================================");
        System.out.println(errors.size() + " błędów");
        System.out.println("==============================================");
        errors.forEach(error -> System.out.println(error));
        System.out.println("==============================================");
        return channelList;
    }


}

