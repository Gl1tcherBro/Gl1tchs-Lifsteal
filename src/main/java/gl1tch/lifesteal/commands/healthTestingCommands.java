package gl1tch.lifesteal.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import gl1tch.lifesteal.util.IEntityDataSaver;
import gl1tch.lifesteal.util.lifestealForceRefreshBool;
import gl1tch.lifesteal.util.lifestealHeartCountFloat;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;

public class healthTestingCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("heartsInc").requires((source) -> {
                    return source.hasPermissionLevel(3);
                })
                .then(CommandManager.argument("targets", EntityArgumentType.players())
                        .then(CommandManager.argument("num", IntegerArgumentType.integer())
                                .executes(context -> executesInc(EntityArgumentType.getPlayers(context, "targets"), IntegerArgumentType.getInteger(context, "num"), context.getSource().getPlayer())))));

        dispatcher.register(CommandManager.literal("heartsDec").requires((source) -> {
                    return source.hasPermissionLevel(3);
                })
                .then(CommandManager.argument("targets", EntityArgumentType.players())
                        .then(CommandManager.argument("num", IntegerArgumentType.integer())
                                .executes(context -> executesDec(EntityArgumentType.getPlayers(context, "targets"), IntegerArgumentType.getInteger(context, "num"), context.getSource().getPlayer())))));

        dispatcher.register(CommandManager.literal("heartsForceRefresh").requires((source) -> {
                    return source.hasPermissionLevel(3);
                })
                .then(CommandManager.argument("targets", EntityArgumentType.players())
                        .then(CommandManager.argument("bool", BoolArgumentType.bool())
                                .executes(context -> executesRefresh(EntityArgumentType.getPlayers(context, "targets"), BoolArgumentType.getBool(context, "bool"), context.getSource().getPlayer())))));
    }

    private static int executesInc(Collection<ServerPlayerEntity> targets, int num, ServerPlayerEntity sender) {

        for (ServerPlayerEntity player : targets) {
            lifestealHeartCountFloat.increment((IEntityDataSaver) player, num);
        }

        return 0;
    }

    private static int executesDec(Collection<ServerPlayerEntity> targets, int num, ServerPlayerEntity sender) {

        for (ServerPlayerEntity player : targets) {
            lifestealHeartCountFloat.decrement((IEntityDataSaver) player, num);
        }

        return 0;
    }

    private static int executesRefresh(Collection<ServerPlayerEntity> targets, boolean bool, ServerPlayerEntity sender) {

        for (ServerPlayerEntity player : targets) {
            lifestealForceRefreshBool.set((IEntityDataSaver) player, bool);
        }

        return 0;
    }
}
