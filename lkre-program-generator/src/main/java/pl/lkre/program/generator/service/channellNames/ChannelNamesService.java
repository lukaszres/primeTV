package pl.lkre.program.generator.service.channellNames;

import java.util.ArrayList;
import java.util.List;

public class ChannelNamesService {

    public List<String> getAllChannelNames() {
        List<String> listOfChannels = new ArrayList<>();
        for (ChannelName channel : ChannelName.values()) {
            listOfChannels.add(channel.getValue());
        }
        return listOfChannels;
    }
}
