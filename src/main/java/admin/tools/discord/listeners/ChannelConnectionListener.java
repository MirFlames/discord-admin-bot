package admin.tools.discord.listeners;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.VoiceStateUpdateEvent;
import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelCreateSpec;
import discord4j.gateway.ShardInfo;
import discord4j.rest.util.PermissionSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Slf4j
@Service
public class ChannelConnectionListener implements EventListener<VoiceStateUpdateEvent> {

    private ButtonInteractionEvent buttonInteractionEvent;
    private MessageCreateEvent messageCreateEvent;

    @Override
    public Class<VoiceStateUpdateEvent> getEventType() {
        return VoiceStateUpdateEvent.class;
    }

    @Override
    public Mono<Void> handleError(Throwable error) {
        return EventListener.super.handleError(error);
    }

    private void createNewChannelInCurrentCategory() {

    }

    private Supplier<Boolean> isBotOccupiedChannel() {
        return null;
    }

    @Override
    public Mono<Void> execute(VoiceStateUpdateEvent event) {

        if ((event.isLeaveEvent() || event.isMoveEvent()) &&
                !event.getOld().orElseThrow().getChannel().block().getName().equals("Создать канал") && // TODO: тупа чтобы работало кароче
                event.getOld().orElseThrow().getChannel().block().getCategoryId().orElseThrow().asLong() == 1113876235047677983L && // TODO: тоже
                !event.getOld().orElseThrow().getChannel().block().getVoiceStates().hasElements().block().booleanValue()) {
            event.getOld().orElseThrow().getChannel().flatMap(Channel::delete).log().subscribe();
        } else if ((event.isJoinEvent() || event.isMoveEvent()) && event.getCurrent().getChannel().block().getName().equals("Создать канал")) {
            var current = event.getCurrent();
            var userId = current.getUserId();
            var username = current.getData().member().get().user().username();

            var shardInfo = event.getShardInfo();
            var guild = event.getCurrent().getGuild().blockOptional().orElseThrow();

            VoiceChannelCreateSpec voiceChannelCreateSpec = VoiceChannelCreateSpec
                    .builder()
                    .name(username)
                    .addPermissionOverwrite(PermissionOverwrite.forMember(userId, PermissionSet.all(), PermissionSet.none()))
                    .parentId(current.getChannel().blockOptional().orElseThrow().getCategoryId().orElseThrow()) // 1113876235047677983
                    .build();
            Member member = event.getCurrent().getMember().blockOptional().orElseThrow();
            VoiceChannel voiceChannel = guild.createVoiceChannel(voiceChannelCreateSpec).blockOptional().orElseThrow();
            member.edit(spec -> spec.setNewVoiceChannel(voiceChannel.getId())).block();
        }
        return Mono.just(event).then();
    }

}

//VoiceStateUpdateEvent