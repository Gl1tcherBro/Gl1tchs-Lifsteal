package gl1tch.lifesteal.mixin;

import gl1tch.lifesteal.util.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


/*
This will no longer be used as of 09/05/2024 (MM/DD/YYYY) due to
IEntityDataSaver not doing what I need, sadly that means a complete
rework is needed as of the time I am typing this.
 */

@Mixin(Entity.class)
public abstract class NBTStuffs implements IEntityDataSaver {
    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData() {
        if(this.persistentData == null) {
            this.persistentData = new NbtCompound();
        }
        return persistentData;
    }
    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfo info) {
        if(persistentData != null) {
            nbt.put("lifesteal", persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("lifesteal", NbtCompound.COMPOUND_TYPE)) {
            persistentData = nbt.getCompound("lifesteal");
        }
    }
}
