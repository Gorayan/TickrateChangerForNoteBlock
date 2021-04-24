package com.gmail.gorayan3838.tickratechangerfornoteblock.mixin;

import com.gmail.gorayan3838.tickratechangerfornoteblock.TickrateChangerForNoteBlock;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @ModifyConstant(method = {"run()V"}, constant = {@Constant(longValue = 50L)})
    private long modifyTickTime(long tickTime) {
        return TickrateChangerForNoteBlock.getTickTime();
    }
}
