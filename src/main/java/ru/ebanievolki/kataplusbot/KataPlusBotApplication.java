package ru.ebanievolki.kataplusbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class KataPlusBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(KataPlusBotApplication.class, args);
    }

}
