package gl1tch.lifesteal.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import gl1tch.lifesteal.item.ModItems;
import gl1tch.lifesteal.util.IEntityDataSaver;
import gl1tch.lifesteal.util.lifestealHeartCountFloat;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static gl1tch.lifesteal.util.LifestealForceRefresh.forceRefresh;

public class heartCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("withdraw")
                        .then(CommandManager.argument("hearts", IntegerArgumentType.integer())
                                .executes(context -> executesGet(context.getSource().getPlayer(), IntegerArgumentType.getInteger(context, "hearts")))));
    }


    //This code is still a little buggy
    private static int executesGet(ServerPlayerEntity player, int num) {
        float heartCount = lifestealHeartCountFloat.getState((IEntityDataSaver) player);
        ItemStack heartItemStack = ModItems.HEART_ITEM.getDefaultStack();

        if (num > 128) {
            player.sendMessage(Text.of("§c[Error]: Number of hearts to withdraw too large the max is 128 when you put " + num + "!"));
            return 0;
        } else if (num < 0) {
            player.sendMessage(Text.of("§c[Error]: You cannot withdraw negative hearts!"));
            return 0;
        }

        if (heartCount / 2 > num) {
            lifestealHeartCountFloat.decrement((IEntityDataSaver) player, num * 2);
            forceRefresh(player);
            if (player.getInventory().contains(Items.AIR.getDefaultStack())) {
                if (num > 64) {
                    int newNum = num - 64;
                    heartItemStack.setCount(newNum);
                    player.giveItemStack(heartItemStack);
                } else {
                    heartItemStack.setCount(num);
                    player.giveItemStack(heartItemStack);
                    num -= num;
                }
                if (num > 0) {
                    if (player.getInventory().contains(Items.AIR.getDefaultStack())) {
                        heartItemStack.setCount(num);
                        player.giveItemStack(heartItemStack);
                    } else {
                        heartItemStack.setCount(num);
                        player.dropStack(heartItemStack);
                    }
                }
            } else {
                if (num > 64) {
                    int newNum = num - 64;
                    heartItemStack.setCount(newNum);
                    player.dropStack(heartItemStack);
                } else {
                    heartItemStack.setCount(num);
                    player.dropStack(heartItemStack);
                    num -= num;
                }
                if (num > 0) {
                    heartItemStack.setCount(num);
                    player.dropStack(heartItemStack);
                }
            }
        } else {
            player.sendMessage(Text.of("§c[Error]: You do not have enough hearts to withdraw " + num + " hearts!"));
            return 0;
        }

        return 1;
    }
}
