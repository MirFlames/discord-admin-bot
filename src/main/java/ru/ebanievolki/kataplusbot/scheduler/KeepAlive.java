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
    JDA jda;

    @Scheduled(fixedRate = 600000)
    public void wakeUpBot() {
        jda.getTextChannels();
        log.info("Бот по прежнему жив");
    }
}
