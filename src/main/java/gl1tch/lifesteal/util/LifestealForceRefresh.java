package gl1tch.lifesteal.util;

import net.minecraft.server.network.ServerPlayerEntity;

import static gl1tch.lifesteal.util.lifestealForceRefreshBool.*;

public class LifestealForceRefresh {
    public static void forceRefresh(ServerPlayerEntity player) {
        set((IEntityDataSaver) player, true);
    }

    public static void forceRefresh(IEntityDataSaver player) {
        set(player, true);
    }
}
