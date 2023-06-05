package admin.tools.discord.listeners;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.VoiceStateUpdateEvent;
import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelCreateSpec;
import discord4j.gateway.ShardInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class ChannelConnectionListener implements EventListener<VoiceStateUpdateEvent> {

    private static List<String> namesChannel;

    static {
        namesChannel.add("Pre-Project");
        namesChannel.add("\uD83C\uDF03 code with lo-fi everyday");
    }

    ButtonInteractionEvent buttonInteractionEvent;
    MessageCreateEvent messageCreateEvent;

    @Override
    public Class<VoiceStateUpdateEvent> getEventType() {
        return VoiceStateUpdateEvent.class;
    }

    @Override
    public Mono<Void> handleError(Throwable error) {
        return EventListener.super.handleError(error);
    }

    @Override
    public Mono<Void> execute(VoiceStateUpdateEvent event) {

        String nameChannel = "not found";

        VoiceState current = event.getCurrent();
        Mono<User> user = current.getUser();
        Snowflake userId = current.getUserId();
        GatewayDiscordClient client = current.getClient();
        String username = current.getData().member().get().user().username();
        ShardInfo shardInfo = event.getShardInfo();

        try {
            nameChannel = event.getCurrent().getChannel().block().getName();
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }

        Guild guild = event.getCurrent().getGuild().block();

        if (nameChannel.equals("Создать канал")) {
            System.out.println("user - " + user);
            System.out.println("userId - " + userId);
            System.out.println("client - " + client);
            System.out.println("username - " + username);
            System.out.println("shardInfo - " + shardInfo.format());
            System.out.println("name channel - " + nameChannel);

            VoiceChannelCreateSpec voiceChannelCreateSpec = VoiceChannelCreateSpec
                    .builder()
                    .name(username)
                    .userLimit(10)
                    .build();


            Member member = event.getCurrent().getMember().block();

            System.out.println("guild - " + guild.getName());
            System.out.println("member - " + member.getUsername());

            VoiceChannel voiceChannel = guild.createVoiceChannel(voiceChannelCreateSpec).block();

            member.edit(spec -> spec.setNewVoiceChannel(voiceChannel.getId())).block();
        }

        VoiceState voiceState = new VoiceState(client, event.getCurrent().getData());

//        guild.getChannels().ofType(VoiceChannel.class)
//                .filter(channel -> channel.getName().equals(username))
//                .flatMap(Channel::delete)
//                .subscribe();

        assert guild != null;
        guild.getChannels().ofType(VoiceChannel.class)
                .map(ch -> ch.getName())
                .toStream()
                .toList()
                .forEach(System.out::println);


        return Mono.just(event).filter(e -> (e.isJoinEvent() || e.isMoveEvent()) &&
                                            e.getCurrent().getChannel().block().getName().equals("Создать канал")).then();
    }

}

//VoiceStateUpdateEvent