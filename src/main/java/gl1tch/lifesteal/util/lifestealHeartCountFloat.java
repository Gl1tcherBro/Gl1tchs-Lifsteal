package gl1tch.lifesteal.util;

import net.minecraft.nbt.NbtCompound;

import static gl1tch.lifesteal.util.ServerDefaults.*;

public class lifestealHeartCountFloat {
    public static float set(IEntityDataSaver player, float num) {
        NbtCompound nbt = player.getPersistentData();
        float lifestealHeartsFloat = num;

        nbt.putFloat("lifestealHeartsFloat", lifestealHeartsFloat);
        // sync the data
        return lifestealHeartsFloat;
    }

    public static float increment(IEntityDataSaver player, float num) {
        NbtCompound nbt = player.getPersistentData();
        float lifestealHeartsFloat = nbt.getFloat("lifestealHeartsFloat");

        lifestealHeartsFloat += num;
        if (lifestealHeartsFloat >= maxHealth & maxHealth != -1) {
            lifestealHeartsFloat = maxHealth;
        }

        nbt.putFloat("lifestealHeartsFloat", lifestealHeartsFloat);
        // sync the data
        return lifestealHeartsFloat;
    }

    public static float decrement(IEntityDataSaver player, float num) {
        NbtCompound nbt = player.getPersistentData();
        float lifestealHeartsFloat = nbt.getFloat("lifestealHeartsFloat");
        lifestealHeartsFloat -= num;

        if (lifestealHeartsFloat <= minHealth) {
            lifestealHeartsFloat = minHealth;
            lifestealBannedBool.set(player, true);
        }

        nbt.putFloat("lifestealHeartsFloat", lifestealHeartsFloat);
        // sync the data
        return lifestealHeartsFloat;
    }

    public static float getState(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();

        return nbt.getFloat("lifestealHeartsFloat");
    }
}
