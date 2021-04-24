package com.gmail.gorayan3838.tickratechangerfornoteblock;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class TickrateCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("tickrate")
                .requires(commandSource -> commandSource.hasPermissionLevel(2))
                .then(Commands.literal("set")
                        .then(Commands.argument("tick", FloatArgumentType.floatArg(0.1f, 1000f))
                                .executes(context -> set(context, FloatArgumentType.getFloat(context, "tick")))
                        )
                )
                .then(Commands.literal("get")
                        .executes(context -> get(context))
                )
        );
    }

    public static int set(CommandContext<CommandSource> context, float tickrate) {
        TickrateChangerForNoteBlock.update((float) Math.max(Math.min(1000, tickrate), 0.1));
        context.getSource().sendFeedback(new TranslationTextComponent("commands.tickrate.set", TickrateChangerForNoteBlock.getTickrate()), true);
        return 1;
    }

    public static int get(CommandContext<CommandSource> context) {
        context.getSource().sendFeedback(new TranslationTextComponent("commands.tickrate.get", TickrateChangerForNoteBlock.getTickrate()), true);
        return 1;
    }
}
