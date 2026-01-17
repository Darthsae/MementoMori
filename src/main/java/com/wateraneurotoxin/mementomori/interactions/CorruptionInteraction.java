package com.wateraneurotoxin.mementomori.interactions;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.wateraneurotoxin.mementomori.MementoMoriPlugin;

import javax.annotation.Nonnull;

public class CorruptionInteraction extends SimpleInstantInteraction {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static final BuilderCodec<CorruptionInteraction> CODEC =
        ((BuilderCodec.Builder) BuilderCodec.builder(
            CorruptionInteraction.class,
            CorruptionInteraction::new,
            SimpleInstantInteraction.CODEC)
            .appendInherited(
                new KeyedCodec<>("Amount", Codec.FLOAT),
                (i, v) -> i.amount = v.floatValue(),
                i -> i.amount,
                (i, parent) -> i.amount = parent.amount)
            .add())
        .build();

    private float amount = 0;

    @SuppressWarnings("null")
    @Override
    protected void firstRun(@Nonnull InteractionType type,
                            @Nonnull InteractionContext context,
                            @Nonnull CooldownHandler cooldownHandler) {

        Ref<EntityStore> entity = context.getEntity();
        CommandBuffer<EntityStore> buffer = context.getCommandBuffer();

        context.getState().state = MementoMoriPlugin.INSTANCE.changeCorruption(entity, buffer.getStore(), horizontalSpeedMultiplier) ? InteractionState.Finished : InteractionState.Failed;
    }
}