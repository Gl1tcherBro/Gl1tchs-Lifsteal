package gl1tch.lifesteal.util;

import net.minecraft.nbt.NbtCompound;

public class lifestealBannedBool {
    public static boolean set(IEntityDataSaver player, boolean state) {
        NbtCompound nbt = player.getPersistentData();
        boolean lifestealBannedBool = nbt.getBoolean("lifestealBannedBool");

        lifestealBannedBool = state;

        nbt.putBoolean("lifestealBannedBool", lifestealBannedBool);
        // sync the data
        return lifestealBannedBool;
    }

    public static boolean getState(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();

        return nbt.getBoolean("lifestealBannedBool");
    }
}
