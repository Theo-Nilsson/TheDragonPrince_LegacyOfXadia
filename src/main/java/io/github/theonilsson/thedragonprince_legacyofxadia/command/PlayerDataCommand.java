package io.github.theonilsson.thedragonprince_legacyofxadia.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.theonilsson.thedragonprince_legacyofxadia.LOX;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class PlayerDataCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("playerdata")
                        .requires((cs) -> cs.hasPermission(2))
                        .then(
                                Commands.literal("get")
                                        .then(
                                                Commands.argument("targets", EntityArgument.players())
                                                        .executes(PlayerDataCommand::get_data)
                                        )
                        )
                        .then(
                                Commands.literal("clear")
                                        .then(Commands.argument("targets", EntityArgument.player())
                                                .executes(PlayerDataCommand::clear_data))
                        )
        );
    }

    private static int get_data(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> targets = EntityArgument.getPlayers(context, "targets");
        for (ServerPlayer target : targets) {
            Objects.requireNonNull(context.getSource().getPlayer())
                    .sendSystemMessage(Component.literal(target.getDisplayName().getString() + ": " + Component.literal(target.getPersistentData().toString()).getString()));
            LOX.LOGGER.info("{}: {}", target.getDisplayName().getString(), target.getPersistentData());
        }
        return 1;
    }

    private static int clear_data(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> targets = EntityArgument.getPlayers(context, "targets");
        for (ServerPlayer target : targets) {
            List<String> persistentData = new ArrayList<>(target.getPersistentData().getAllKeys());
            for (String tag : persistentData) {
                target.getPersistentData().remove(tag);
            }
            LOX.LOGGER.info("{}: Cleared player data.", target.getDisplayName().getString());
        }
        return 1;
    }
}
