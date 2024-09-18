package gl1tch.lifesteal.util;

import net.minecraft.nbt.NbtCompound;

public class lifestealForceRefreshBool {
    public static boolean set(IEntityDataSaver player, boolean state) {
        NbtCompound nbt = player.getPersistentData();
        boolean lifestealForceRefreshBool = nbt.getBoolean("lifestealForceRefreshBool");

        lifestealForceRefreshBool = state;

        nbt.putBoolean("lifestealForceRefreshBool", lifestealForceRefreshBool);
        // sync the data
        return lifestealForceRefreshBool;
    }

    public static boolean getState(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();

        return nbt.getBoolean("lifestealForceRefreshBool");
    }
}
