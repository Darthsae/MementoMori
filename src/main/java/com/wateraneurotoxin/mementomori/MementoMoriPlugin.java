package com.wateraneurotoxin.mementomori;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
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
import com.wateraneurotoxin.mementomori.components.KnowledgeOfDeathComponent;
import com.wateraneurotoxin.mementomori.events.ExampleEvent;
import com.wateraneurotoxin.mementomori.interactions.CorruptionInteraction;
import com.wateraneurotoxin.mementomori.interactions.OpenLorePageInteraction;
import com.wateraneurotoxin.mementomori.systems.KnowledgeOfDeathEventSystem;
import com.wateraneurotoxin.mementomori.ui.CorruptionHUD;

import javax.annotation.Nonnull;

public class MementoMoriPlugin extends JavaPlugin {
    public static MementoMoriPlugin INSTANCE;
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    
    public static ComponentType<EntityStore, KnowledgeOfDeathComponent> KNOWLEDGE_OF_DEATH_COMPONENT_TYPE;

    private int corruptionStatIndex = Integer.MIN_VALUE;

    public MementoMoriPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        INSTANCE = this;

        KNOWLEDGE_OF_DEATH_COMPONENT_TYPE = getEntityStoreRegistry().registerComponent(
            KnowledgeOfDeathComponent.class,
            "KnowledgeOfDeath",
            KnowledgeOfDeathComponent.CODEC
        );

        this.getCommandRegistry().registerCommand(new ExampleCommand("corruptionhud", "An example command"));
        
        this.getEntityStoreRegistry().registerSystem(new KnowledgeOfDeathEventSystem());

        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, ExampleEvent::onPlayerReady);

        this.getCodecRegistry(Interaction.CODEC).register("Corruption", CorruptionInteraction.class, CorruptionInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("OpenLorePage", OpenLorePageInteraction.class, OpenLorePageInteraction.CODEC);
    
        this.getEventRegistry().registerGlobal(PlayerDisconnectEvent.class, (event) -> {
            CorruptionHUD.removeHud(event.getPlayerRef());
        });
    }
    
    @SuppressWarnings("null")
    public boolean changeCorruption(Ref<EntityStore> a_ref, Store<EntityStore> a_store, float a_amount) {
        if (corruptionStatIndex == Integer.MIN_VALUE) {
            corruptionStatIndex = EntityStatType.getAssetMap().getIndex("Corruption");
        }

        EntityStatMap stats = a_store.getComponent(a_ref, EntityStatMap.getComponentType());
        if (stats == null) return false;

        EntityStatValue corruption = stats.get(corruptionStatIndex);
        if (corruption == null) {
            return false;
        }

        stats.addStatValue(Predictable.SELF, corruptionStatIndex, a_amount);

        return true;
    }
    
    @SuppressWarnings("null")
    public float getCorruption(Ref<EntityStore> a_ref, Store<EntityStore> a_store) {
        if (corruptionStatIndex == Integer.MIN_VALUE) {
            corruptionStatIndex = EntityStatType.getAssetMap().getIndex("Corruption");
        }

        EntityStatMap stats = a_store.getComponent(a_ref, EntityStatMap.getComponentType());
        if (stats == null) return 0;

        EntityStatValue corruption = stats.get(corruptionStatIndex);
        if (corruption == null) {
            return 0;
        }

        return stats.get(corruptionStatIndex).get();
    }

    @SuppressWarnings("null")
    public float getMaxCorruption(Ref<EntityStore> a_ref, Store<EntityStore> a_store) {
        if (corruptionStatIndex == Integer.MIN_VALUE) {
            corruptionStatIndex = EntityStatType.getAssetMap().getIndex("Corruption");
        }

        EntityStatMap stats = a_store.getComponent(a_ref, EntityStatMap.getComponentType());
        if (stats == null) return 1;

        EntityStatValue corruption = stats.get(corruptionStatIndex);
        if (corruption == null) {
            return 1;
        }

        return stats.get(corruptionStatIndex).getMax();
    }
}