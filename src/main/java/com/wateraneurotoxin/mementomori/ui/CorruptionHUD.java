package com.wateraneurotoxin.mementomori.ui;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.wateraneurotoxin.mementomori.MementoMoriPlugin;

import java.util.WeakHashMap;

import javax.annotation.Nonnull;

import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class CorruptionHUD extends CustomUIHud {
    public static final String ID = "MementoMori:CorruptionHUD";

    public static final WeakHashMap<PlayerRef, CorruptionHUD> hudMap = new WeakHashMap<PlayerRef, CorruptionHUD>();

    public CorruptionHUD(@Nonnull PlayerRef a_playerRef) {
        super(a_playerRef);
        hudMap.put(a_playerRef, this);
    }

    @Override
    protected void build(@Nonnull UICommandBuilder uiCommandBuilder) {
        uiCommandBuilder.append("CorruptionHUD.ui");
        update(uiCommandBuilder);
    }

    public void update(UICommandBuilder a_uiCommandBuilder) {
        Ref<EntityStore> playerRef = getPlayerRef().getReference();
        Store<EntityStore> store = playerRef.getStore();

        a_uiCommandBuilder.set("#ProgressBar.Value", MementoMoriPlugin.INSTANCE.getCorruption(playerRef, store) / MementoMoriPlugin.INSTANCE.getMaxCorruption(playerRef, store));
        update(false, a_uiCommandBuilder);
    }

    public static void updateHud(@NonNullDecl PlayerRef a_playerRef) {
        CorruptionHUD hud = hudMap.get(a_playerRef);
        if (hud == null)
            return;
        UICommandBuilder uiCommandBuilder = new UICommandBuilder();
        hud.update(uiCommandBuilder);
    }

    public void setLabel(String a_label) {
        UICommandBuilder uiCommandBuilder = new UICommandBuilder();
        uiCommandBuilder.set("#CorruptionHUDLabel.TextSpans", Message.translation(a_label));
        update(false, uiCommandBuilder);
    }

    public static void removeHud(@NonNullDecl PlayerRef a_playerRef) {
        hudMap.remove(a_playerRef);
    }
}