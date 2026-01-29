package com.wateraneurotoxin.mementomori.commands;

import com.buuz135.mhud.MultipleHUD;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.wateraneurotoxin.mementomori.ui.CorruptionHUD;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class ExampleCommand extends AbstractPlayerCommand {
    @Nonnull
    private final RequiredArg<Boolean> showArg = this.withRequiredArg("show", "server.commands.corruption_hud.show.desc", ArgTypes.BOOLEAN);

    public ExampleCommand(String name, String description) {
        super(name, description);
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = context.senderAs(Player.class);

        CompletableFuture.runAsync(() -> {
            if (this.showArg.get(context)) { 
                MultipleHUD.getInstance().setCustomHud(player, playerRef, CorruptionHUD.ID, new CorruptionHUD(playerRef));
            } else { 
                MultipleHUD.getInstance().hideCustomHud(player, playerRef, CorruptionHUD.ID);
            }
        }, world);
    }

}
