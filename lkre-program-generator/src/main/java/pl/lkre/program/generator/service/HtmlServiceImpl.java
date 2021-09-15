package pl.lkre.program.generator.service;

import pl.lkre.program.generator.HtmlService;
import pl.lkre.program.generator.service.channel.ChannelListService;
import pl.lkre.program.generator.service.channellNames.ChannelNamesService;
import pl.lkre.program.tv.model.Channel;
import pl.lkre.program.tv.model.Seance;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HtmlServiceImpl implements HtmlService {
    private final ChannelNamesService channelNamesService = new ChannelNamesService();
    private final ChannelListService channelListService = new ChannelListService();
    private final SeanceService seanceService = new SeanceService();


    @Override
    public List<Seance> downloadSeances() {
        List<String> channelNames = channelNamesService.getAllChannelNames();
        //         TODO LukRes 2018-11-07: implement adding to database
        return getSeances(channelNames);
    }

    private List<Seance> getSeances(List<String> channelNames) {
        List<Channel> channelList = channelListService.getChannels(channelNames);
        List<Seance> allSeances = seanceService.getAllSeances(channelList);
        return allSeances.stream()
                .distinct()
                .sorted(Comparator.comparing(Seance::getTime))
                .collect(Collectors.toList());
    }

}

