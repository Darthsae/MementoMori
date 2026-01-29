package com.wateraneurotoxin.mementomori.systems;

import javax.annotation.Nonnull;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.wateraneurotoxin.mementomori.MementoMoriPlugin;
import com.wateraneurotoxin.mementomori.components.KnowledgeOfDeathComponent;

public class KnowledgeOfDeathEventSystem extends DeathSystems.OnDeathSystem {
    public KnowledgeOfDeathEventSystem() {

    }

    @Override
    public Query<EntityStore> getQuery() {
        return Query.any();
    }

    @Override
    public void onComponentAdded(@Nonnull Ref<EntityStore> ref, @Nonnull DeathComponent component, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        Damage deathInfo = component.getDeathInfo();
        if (deathInfo == null) {
            return;
        }

        if (deathInfo.getSource() instanceof Damage.EntitySource entitySource) {
            Ref<EntityStore> attackerRef = entitySource.getRef();
            if (attackerRef.isValid()) {
                KnowledgeOfDeathComponent attackerKnowledgeOfDeath = commandBuffer.ensureAndGetComponent(attackerRef, MementoMoriPlugin.KNOWLEDGE_OF_DEATH_COMPONENT_TYPE);
                KnowledgeOfDeathComponent victimKnowledgeOfDeathComponent = store.getComponent(ref, MementoMoriPlugin.KNOWLEDGE_OF_DEATH_COMPONENT_TYPE);
                if (victimKnowledgeOfDeathComponent == null) {
                    EntityStatMap victimStats = store.getComponent(ref, EntityStatMap.getComponentType());
                    if (victimStats == null) {
                        return;
                    }
                    EntityStatValue healthStat = victimStats.get(EntityStatType.getAssetMap().getIndex("Health"));
                    if (healthStat == null) {
                        return;
                    }

                    attackerKnowledgeOfDeath.addKnowledgeOfDeath((int)Math.ceil(Math.log(healthStat.getMax())));
                } else {
                    attackerKnowledgeOfDeath.addKnowledgeOfDeath(victimKnowledgeOfDeathComponent.getKnowledgeOfDeath());
                    victimKnowledgeOfDeathComponent.clearKnowledgeOfDeath();
                }
            }
        }
    }
}
