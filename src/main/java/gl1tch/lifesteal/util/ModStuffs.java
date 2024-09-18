package gl1tch.lifesteal.util;

import gl1tch.lifesteal.item.ModItemGroups;
import gl1tch.lifesteal.item.ModItems;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import gl1tch.lifesteal.commands.*;

import static gl1tch.lifesteal.util.ServerDefaults.*;

public class ModStuffs {
    protected static final String NBT_KEY = "lifesteal";

    private static void init() {
        minHealth = 0;
        maxHealth = -1;
        waitCount = 4;
    }

    public static void register() {
        init();
//        CommandRegistrationCallback.EVENT.register(healthTestingCommands::register);
        CommandRegistrationCallback.EVENT.register(heartCommands::register);
        ModItems.registerModItems();
        ModItemGroups.registeritemGroups();
        serverTickEvents.registerTickEvents();
    }
}
