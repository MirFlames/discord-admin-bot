package ru.ebanievolki.kataplusbot.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Slf4j
public class KeepAlive {
    JDA jda;

    @Scheduled(fixedRate = 600000)
    public void wakeUpBot() {
        jda.getTextChannels();
        log.info("Бот по прежнему жив");
    }
}
