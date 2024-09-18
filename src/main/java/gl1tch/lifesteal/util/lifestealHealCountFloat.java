package gl1tch.lifesteal.util;

import net.minecraft.nbt.NbtCompound;

import static gl1tch.lifesteal.util.ServerDefaults.maxHealth;
import static gl1tch.lifesteal.util.ServerDefaults.minHealth;

public class lifestealHealCountFloat {
    public static float set(IEntityDataSaver player, float num) {
        NbtCompound nbt = player.getPersistentData();
        float lifestealHealCountFloat = num;

        nbt.putFloat("lifestealHealCountFloat", lifestealHealCountFloat);
        // sync the data
        return lifestealHealCountFloat;
    }

    public static float increment(IEntityDataSaver player, float num) {
        NbtCompound nbt = player.getPersistentData();
        float lifestealHealCountFloat = nbt.getFloat("lifestealHealCountFloat");

        lifestealHealCountFloat += num;

        nbt.putFloat("lifestealHealCountFloat", lifestealHealCountFloat);
        // sync the data
        return lifestealHealCountFloat;
    }

    public static float decrement(IEntityDataSaver player, float num) {
        NbtCompound nbt = player.getPersistentData();
        float lifestealHealCountFloat = nbt.getFloat("lifestealHealCountFloat");

        lifestealHealCountFloat -= num;

        nbt.putFloat("lifestealHealCountFloat", lifestealHealCountFloat);
        // sync the data
        return lifestealHealCountFloat;
    }

    public static float getState(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();

        return nbt.getFloat("lifestealHealCountFloat");
    }
}
