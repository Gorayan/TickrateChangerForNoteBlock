package com.gmail.gorayan3838.tickratechangerfornoteblock;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class ServerEventListener {
    @SubscribeEvent
    public static void onServerStart(FMLServerStartingEvent event) {
        TickrateCommand.register(event.getCommandDispatcher());
    }
}
