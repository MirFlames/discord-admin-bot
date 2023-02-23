package ru.ebanievolki.kataplusbot.configuration;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ebanievolki.kataplusbot.Handlers.MessageHandler;

import javax.security.auth.login.LoginException;

@Configuration
public class JDAConfig {

    @Bean
    public JDA configJDA(MessageHandler messageHandler) throws LoginException {
        return JDABuilder.createDefault("OTkwMTQwMjU1NzE1ODY0NjE4.GRvM2_.awhm1Cf7WQDJ67V_9BA-1xv-pQEo3npEkeHS4I")
                .setActivity(Activity.playing("Calculator"))
                .addEventListeners()
                .addEventListeners(messageHandler).build();
    }
}
