package com.wateraneurotoxin.mementomori.ui;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.wateraneurotoxin.mementomori.datastructures.LoreData;

public class LorePage extends InteractiveCustomUIPage<LorePage.LorePageEventData> {
   private final LoreData loreData;

   @SuppressWarnings("null")
   public LorePage(PlayerRef a_playerRef, LoreData a_loreData) {
      super(a_playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction, LorePageEventData.CODEC);
      this.loreData = a_loreData;
   }

   @SuppressWarnings("null")
   @Override
   public void build(Ref<EntityStore> ref, UICommandBuilder commands, UIEventBuilder events, Store<EntityStore> store) {
      //MementoMoriPlugin.LOGGER.atInfo().log("Opening lore page of: " + this.loreData.name + " with " + this.loreData.pageCount + " pages.");
      commands.append("LorePage.ui");

      commands.set("#TitleText.Text", Message.translation("server.lore." + loreData.name + ".title"));
      commands.set("#AuthorText.Text", Message.translation("server.lore." + loreData.name + ".author"));
      commands.set("#ContentText.Text", Message.translation("server.lore." + loreData.name + ".page0.text"));

      commands.set("#PreviousButton.TextSpans", Message.raw("Close"));
      events.addEventBinding(CustomUIEventBindingType.Activating, "#PreviousButton",  EventData.of("Page", String.valueOf(-1)));
      commands.set("#NextButton.TextSpans", Message.raw(1 < loreData.pageCount ? "Next" : "Close"));
      events.addEventBinding(CustomUIEventBindingType.Activating, "#NextButton",  EventData.of("Page", String.valueOf(1)));
   }

   @SuppressWarnings("null")
   @Override
   public void handleDataEvent(Ref<EntityStore> ref, Store<EntityStore> store, LorePageEventData data) {
      //MementoMoriPlugin.LOGGER.atInfo().log("Data {" + data.close + ", " + data.page + "}");
      if (data.page >= 0 && data.page < loreData.pageCount) {
         UICommandBuilder commands = new UICommandBuilder();
         commands.set("#ContentText.Text", Message.translation("server.lore." + loreData.name + ".page" + data.page + ".text"));

         UIEventBuilder events = new UIEventBuilder();
         commands.set("#PreviousButton.TextSpans", Message.raw(data.page - 1 >= 0 ? "Previous" : "Close"));
         events.addEventBinding(CustomUIEventBindingType.Activating, "#PreviousButton", EventData.of("Page", String.valueOf(data.page - 1)));
         commands.set("#NextButton.TextSpans", Message.raw(data.page + 1 < loreData.pageCount ? "Next" : "Close"));
         events.addEventBinding(CustomUIEventBindingType.Activating, "#NextButton", EventData.of("Page", String.valueOf(data.page + 1)));

         this.sendUpdate(commands, events, false);
      } else {
         this.close();
      }
   }

   public static class LorePageEventData {
      public static final BuilderCodec<LorePageEventData> CODEC = BuilderCodec
         .builder(LorePageEventData.class, LorePageEventData::new)
         .append(new KeyedCodec<>("Page", Codec.STRING), (data, s) -> data.page = Integer.parseInt(s), data -> String.valueOf(data.page))
         .add()
         .build();
      
      public int page;
      public boolean close;
   }
}