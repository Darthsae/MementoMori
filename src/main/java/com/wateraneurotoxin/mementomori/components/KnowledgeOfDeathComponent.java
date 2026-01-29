package com.wateraneurotoxin.mementomori.components;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class KnowledgeOfDeathComponent implements Component<EntityStore> {
    public static final BuilderCodec<KnowledgeOfDeathComponent> CODEC =
        BuilderCodec.builder(KnowledgeOfDeathComponent.class, KnowledgeOfDeathComponent::new)
            .append(new KeyedCodec<>("KnowledgeOfDeath", Codec.INTEGER),
                (c, v) -> c.m_knowledgeOfDeath = v, c -> c.m_knowledgeOfDeath)
            .add()
            .build();

    private int m_knowledgeOfDeath;

    public KnowledgeOfDeathComponent() {}

    public KnowledgeOfDeathComponent(int a_knowledgeOfDeath) {
        this.m_knowledgeOfDeath = a_knowledgeOfDeath;
    }

    public int getKnowledgeOfDeath() {
        return m_knowledgeOfDeath;
    }

    public void setKnowledgeOfDeath(int a_knowledgeOfDeath) {
        this.m_knowledgeOfDeath = a_knowledgeOfDeath;
    }

    public void addKnowledgeOfDeath(int a_knowledgeOfDeath) {
        this.m_knowledgeOfDeath += a_knowledgeOfDeath;
    }

    public void clearKnowledgeOfDeath() {
        this.m_knowledgeOfDeath = 0;
    }

    @Override
    public Component<EntityStore> clone() {
        return new KnowledgeOfDeathComponent(m_knowledgeOfDeath);
    }
}
