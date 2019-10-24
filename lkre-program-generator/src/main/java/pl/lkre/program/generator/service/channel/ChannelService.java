package pl.lkre.program.generator.service.channel;

import pl.lkre.program.tv.model.Channel;

import java.io.IOException;
import java.text.ParseException;

public interface ChannelService {
    Channel getChannel(String channel) throws IOException, ParseException;
}
