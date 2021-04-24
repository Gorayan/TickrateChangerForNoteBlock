package com.gmail.gorayan3838.tickratechangerfornoteblock.mixin.client;

import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Timer.class)
public interface TimerAccessor {
    @Accessor("tickLength")
    void setTickLength(float tickLength);
}
