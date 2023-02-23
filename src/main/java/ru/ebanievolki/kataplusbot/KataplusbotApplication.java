package ru.ebanievolki.kataplusbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.ebanievolki.kataplusbot.Handlers.MessageHandler;

import javax.security.auth.login.LoginException;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling
public class KataplusbotApplication {

    public static void main(String[] args) throws LoginException {
        SpringApplication.run(KataplusbotApplication.class, args);
    }

}
