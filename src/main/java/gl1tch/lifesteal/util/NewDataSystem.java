package gl1tch.lifesteal.util;
import gl1tch.lifesteal.Lifesteal;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.Objects;

public class NewDataSystem extends PersistentState implements IEntityDataSaver {

    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData() {
        if(this.persistentData == null) {
            this.persistentData = new NbtCompound();
        }
        return persistentData;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        if(persistentData != null) {
            nbt.put(ModStuffs.NBT_KEY, persistentData);
        }
        return nbt;
    }

    public static NewDataSystem createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        NewDataSystem state = new NewDataSystem();
        state.persistentData = tag.getCompound(ModStuffs.NBT_KEY);
        return state;
    }

    private static Type<NewDataSystem> type = new Type<>(
            NewDataSystem::new, // If there's no 'NewDataSystem' yet create one
            NewDataSystem::createFromNbt, // If there is a 'NewDataSystem' NBT, parse it with 'createFromNbt'
            null // Supposed to be an 'DataFixTypes' enum, but we can just pass null
    );

    public static NewDataSystem getServerState(MinecraftServer server) {
        // (Note: arbitrary choice to use 'World.OVERWORLD' instead of 'World.END' or 'World.NETHER'.  Any work)
        PersistentStateManager persistentStateManager = Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getPersistentStateManager();

        // The first time the following 'getOrCreate' function is called, it creates a brand new 'NewDataSystem' and
        // stores it inside the 'PersistentStateManager'. The subsequent calls to 'getOrCreate' pass in the saved
        // 'NewDataSystem' NBT on disk to our function 'NewDataSystem::createFromNbt'.
        NewDataSystem state = persistentStateManager.getOrCreate(type, Lifesteal.MOD_ID);

        // If state is not marked dirty, when Minecraft closes, 'writeNbt' won't be called and therefore nothing will be saved.
        // Technically it's 'cleaner' if you only mark state as dirty when there was actually a change, but the vast majority
        // of mod writers are just going to be confused when their data isn't being saved, and so it's best just to 'markDirty' for them.
        // Besides, it's literally just setting a bool to true, and the only time there's a 'cost' is when the file is written to disk when
        // there were no actual change to any of the mods state (INCREDIBLY RARE).
        state.markDirty();

        return state;
    }
}
