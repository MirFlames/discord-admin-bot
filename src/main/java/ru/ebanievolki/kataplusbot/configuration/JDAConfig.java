package ru.ebanievolki.kataplusbot.configuration;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ebanievolki.kataplusbot.Handlers.MessageHandler;

import javax.security.auth.login.LoginException;

@Configuration
public class JDAConfig {
    @Value("${jda.token}")
    private CharSequence token;
    @Bean
    public JDA configJDA(MessageHandler messageHandler) throws LoginException {
        return JDABuilder.createDefault(token.toString())
                .setActivity(Activity.playing("Java"))
                .addEventListeners()
                .addEventListeners(messageHandler).build();
    }
}
