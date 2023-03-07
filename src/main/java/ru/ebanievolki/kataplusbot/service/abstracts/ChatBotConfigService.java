package ru.ebanievolki.kataplusbot.service.abstracts;

import net.dv8tion.jda.api.entities.MessageEmbed;

public interface ChatBotConfigService {
    MessageEmbed getState();
}
