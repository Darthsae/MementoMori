package com.wateraneurotoxin.mementomori.interactions;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.wateraneurotoxin.mementomori.datastructures.LoreData;
import com.wateraneurotoxin.mementomori.ui.LorePage;

import javax.annotation.Nonnull;

public class OpenLorePageInteraction extends SimpleInstantInteraction {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static final BuilderCodec<OpenLorePageInteraction> CODEC =
        ((BuilderCodec.Builder) BuilderCodec.builder(
            OpenLorePageInteraction.class,
            OpenLorePageInteraction::new,
            SimpleInstantInteraction.CODEC)
            .appendInherited(
                new KeyedCodec<>("Name", Codec.STRING),
                (i, v) -> i.name = v,
                i -> i.name,
                (i, parent) -> i.name = parent.name)
            .add()
            .appendInherited(
                new KeyedCodec<>("PageCount", Codec.INTEGER),
                (i, v) -> i.pages = v,
                i -> i.pages,
                (i, parent) -> i.pages = parent.pages)
            .add())
        .build();

    private String name;
    private int pages;

    @SuppressWarnings("null")
    @Override
    protected void firstRun(@Nonnull InteractionType type,
                            @Nonnull InteractionContext context,
                            @Nonnull CooldownHandler cooldownHandler) {

        Ref<EntityStore> entity = context.getEntity();
        CommandBuffer<EntityStore> buffer = context.getCommandBuffer();
        PlayerRef playerReference = buffer.getComponent(entity, PlayerRef.getComponentType());
        if (playerReference == null) {
            return;
        }

        Player playerComponent = buffer.getComponent(entity, Player.getComponentType());

        if (playerComponent == null) {
            return;
        }

        playerComponent.getPageManager().openCustomPage(entity, buffer.getStore(), new LorePage(playerReference, new LoreData(name, pages)));
    }
}