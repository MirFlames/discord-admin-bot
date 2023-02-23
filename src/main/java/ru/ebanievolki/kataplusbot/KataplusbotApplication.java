package ru.ebanievolki.kataplusbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class KataplusbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(KataplusbotApplication.class, args);
    }

}
