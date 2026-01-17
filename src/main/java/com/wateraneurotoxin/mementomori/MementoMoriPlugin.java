package com.wateraneurotoxin.mementomori;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap.Predictable;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.wateraneurotoxin.mementomori.commands.ExampleCommand;
import com.wateraneurotoxin.mementomori.events.ExampleEvent;
import com.wateraneurotoxin.mementomori.interactions.CorruptionInteraction;

import javax.annotation.Nonnull;

public class MementoMoriPlugin extends JavaPlugin {
    public static MementoMoriPlugin INSTANCE;

    private int corruptionStatIndex = Integer.MIN_VALUE;

    public MementoMoriPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        INSTANCE = this;

        this.getCommandRegistry().registerCommand(new ExampleCommand("example", "An example command"));
        
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, ExampleEvent::onPlayerReady);

        this.getCodecRegistry(Interaction.CODEC).register("Corruption", CorruptionInteraction.class, CorruptionInteraction.CODEC);
    }
    
    @SuppressWarnings("null")
    public boolean changeCorruption(Ref<EntityStore> ref, Store<EntityStore> store, float amount) {
        if (corruptionStatIndex == Integer.MIN_VALUE) {
            corruptionStatIndex = EntityStatType.getAssetMap().getIndex("Corruption");
        }

        EntityStatMap stats = store.getComponent(ref, EntityStatMap.getComponentType());
        if (stats == null) return false;

        EntityStatValue corruption = stats.get(corruptionStatIndex);
        if (corruption == null || corruption.get() < amount) {
            return false;
        }

        stats.addStatValue(Predictable.SELF, corruptionStatIndex, amount);

        return true;
    }
}