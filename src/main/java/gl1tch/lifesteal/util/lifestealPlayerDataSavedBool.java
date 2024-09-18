package gl1tch.lifesteal.util;

import net.minecraft.nbt.NbtCompound;

public class lifestealPlayerDataSavedBool {
    public static boolean set(IEntityDataSaver player, boolean state) {
        NbtCompound nbt = player.getPersistentData();
        boolean lifestealPlayerDataSavedBool = nbt.getBoolean("lifestealPlayerDataSavedBool");

        lifestealPlayerDataSavedBool = state;

        nbt.putBoolean("lifestealPlayerDataSavedBool", lifestealPlayerDataSavedBool);
        // sync the data
        return lifestealPlayerDataSavedBool;
    }

    public static boolean getState(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();

        return nbt.getBoolean("lifestealPlayerDataSavedBool");
    }
}
