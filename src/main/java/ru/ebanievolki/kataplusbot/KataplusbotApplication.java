package ru.ebanievolki.kataplusbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ebanievolki.kataplusbot.Handlers.MessageHandler;

import javax.security.auth.login.LoginException;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class KataplusbotApplication {

    public static void main(String[] args) throws LoginException {
        SpringApplication.run(KataplusbotApplication.class, args);

        JDA jda = JDABuilder.createDefault("OTkwMTQwMjU1NzE1ODY0NjE4.GLIdmD.kjvLAM0rU73qCq5YFGonMLH1XJ36UYjVLDUFvw")
                .setActivity(Activity.playing("Calculator"))
                .addEventListeners(new MessageHandler()).build();
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        jda.getTextChannelsByName("флуд", true).forEach(i -> i.sendMessage("BOT is up and ready to work")
                .timeout(5, TimeUnit.SECONDS)
                .submit());
    }

}
