package gl1tch.lifesteal.mixin;

import gl1tch.lifesteal.util.IEntityDataSaver;
import gl1tch.lifesteal.util.lifestealHealCountFloat;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static gl1tch.lifesteal.util.LifestealForceRefresh.forceRefresh;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Inject(at = @At("HEAD"), method = "onPlayerConnect")
    public void connect(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData clientData, CallbackInfo callbackInfo) {
        forceRefresh(player);
    }

    @Inject(at = @At("HEAD"), method = "remove")
    public void disconnect(ServerPlayerEntity player, CallbackInfo callbackInfo) {
        lifestealHealCountFloat.set((IEntityDataSaver) player, player.getHealth() - 20);
    }
}
