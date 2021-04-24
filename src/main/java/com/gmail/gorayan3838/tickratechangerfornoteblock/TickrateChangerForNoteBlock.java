package com.gmail.gorayan3838.tickratechangerfornoteblock;

import com.gmail.gorayan3838.tickratechangerfornoteblock.mixin.client.MinecraftAccessor;
import com.gmail.gorayan3838.tickratechangerfornoteblock.mixin.client.TimerAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TickrateChangerForNoteBlock.modID)
public class TickrateChangerForNoteBlock {

    public static final String modID = "tickratechangerfornoteblock";

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CNAHNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(modID, "main"),
            () -> "1",
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static float Tickrate = 20.0f;

    public static long getTickTime() {
        return (long) (1000 / Tickrate);
    }

    public static float getTickrate() {
        return Tickrate;
    }

    public static void update(float tickrate) {
        TickrateChangerForNoteBlock.Tickrate = tickrate;
        TickrateChangerForNoteBlock.CNAHNEL.send(PacketDistributor.ALL.noArg(), tickrate);
    }

    @OnlyIn(Dist.CLIENT)
    public static void updateClient(float tickrate) {
        ((TimerAccessor) ((MinecraftAccessor) Minecraft.getInstance()).getTimer()).setTickLength((long) (1000 / tickrate));
    }

    public TickrateChangerForNoteBlock() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        TickrateChangerForNoteBlock.CNAHNEL.registerMessage(
                0,
                Float.class,
                (fload, packetBuffer) -> packetBuffer.writeFloat(fload),
                packetBuffer -> packetBuffer.readFloat(),
                (fload, contextSupplier) -> {
                    contextSupplier.get().enqueueWork(() -> TickrateChangerForNoteBlock.updateClient(fload));
                    contextSupplier.get().setPacketHandled(true);
                }
        );
        MinecraftForge.EVENT_BUS.register(ServerEventListener.class);
    }
}
