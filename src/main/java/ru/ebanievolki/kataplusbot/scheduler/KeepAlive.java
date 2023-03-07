package ru.ebanievolki.kataplusbot.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class KeepAlive {
    private final JDA jda;

    @Scheduled(fixedRate = 600000)
    public void wakeUpBot() {
        log.info("Бот активен в каналах:");
        jda.getTextChannels().forEach(channel -> log.info(channel.getName()));
        jda.updateCommands().addCommands();
    }
}
