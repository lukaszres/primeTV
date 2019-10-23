package pl.lkre.program.generator.service;

import pl.lkre.program.generator.HtmlService;
import pl.lkre.program.generator.constant.ChannelLis;
import pl.lkre.program.generator.model.Channel;
import pl.lkre.program.generator.model.Seance;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ManagedBean
@ViewScoped
public class HtmlServiceImpl implements HtmlService {

    @Override
    public List<Seance> downloadSeances() {
        List<String> channelStringList = getSelectedChannels();
        List<Channel> channelList = new ArrayList<>();
        channelStringList.forEach(
                channel -> {
                    try {
                        channelList.add(ChannelFactory.getChannel(channel));
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
        );
        List<Seance> allSeances = ChannelFactory.getAllSeances(channelList);
        sortByTime(allSeances);
//         TODO LukRes 2018-11-07: implement adding to database
        return allSeances;
    }

    private List<String> getSelectedChannels() {
        List<String> listOfChannels = new ArrayList<>();
        for (ChannelLis channel : ChannelLis.values()) {
            listOfChannels.add(channel.getValue());
        }
        return listOfChannels;
    }

    private void sortByTime(List<Seance> seances) {
        seances.sort(Comparator.comparing(Seance::getTime));
    }
}

