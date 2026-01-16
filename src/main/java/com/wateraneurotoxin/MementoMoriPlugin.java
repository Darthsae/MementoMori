package com.wateraneurotoxin;

import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import com.wateraneurotoxin.commands.ExampleCommand;
import com.wateraneurotoxin.events.ExampleEvent;

import javax.annotation.Nonnull;

public class MementoMoriPlugin extends JavaPlugin {
    public MementoMoriPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        this.getCommandRegistry().registerCommand(new ExampleCommand("example", "An example command"));
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, ExampleEvent::onPlayerReady);
    }
}