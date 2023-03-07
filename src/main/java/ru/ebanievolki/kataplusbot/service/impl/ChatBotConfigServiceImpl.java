package ru.ebanievolki.kataplusbot.service.impl;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import ru.ebanievolki.kataplusbot.service.abstracts.ChatBotConfigService;

import javax.persistence.PersistenceContext;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

@Service
@RequiredArgsConstructor
public class ChatBotConfigServiceImpl implements ChatBotConfigService {
    @Override
    public MessageEmbed getState() {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.addField("uptime",
                String.format("%1$,.2f minutes", rb.getUptime() / 1000. / 60.),
                false);
        embedBuilder.addField("message buffer size",
                "-",
                false);
        return embedBuilder.build();
    }
}
