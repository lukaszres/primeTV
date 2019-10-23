package pl.lkre.program.generator.service;

import pl.lkre.program.generator.constant.ChannelName;

import java.util.ArrayList;
import java.util.List;

class ChannelNamesService {

    List<String> getAllChannelNames() {
        List<String> listOfChannels = new ArrayList<>();
        for (ChannelName channel : ChannelName.values()) {
            listOfChannels.add(channel.getValue());
        }
        return listOfChannels;
    }
}
