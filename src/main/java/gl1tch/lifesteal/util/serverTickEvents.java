package gl1tch.lifesteal.util;


import gl1tch.lifesteal.Lifesteal;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.nio.file.Paths;

import static gl1tch.lifesteal.util.ServerDefaults.*;
import static gl1tch.lifesteal.util.LifestealForceRefresh.*;

public class serverTickEvents {
    private static int curWaitCount;

    public static void registerTickEvents() {
        curWaitCount = waitCount;
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player: server.getPlayerManager().getPlayerList()) {
                if (player.getUuid() == PlayerStuffs.playerUUID) {
                    if (player.isAlive() & curWaitCount == 0) {
                        PlayerStuffs.playerUUID = null;
                        lifestealPlayerDataSavedBool.set((IEntityDataSaver) player, PlayerStuffs.playerHasDataBool);
                        PlayerStuffs.playerHasDataBool = false;
                        lifestealBannedBool.set((IEntityDataSaver) player, PlayerStuffs.playerBannedBool);
                        PlayerStuffs.playerBannedBool = true;
                        lifestealHeartCountFloat.set((IEntityDataSaver) player, PlayerStuffs.playerHeartCountFloat);
                        PlayerStuffs.playerHeartCountFloat = Float.NaN;
                        curWaitCount = waitCount;
                        forceRefresh(player);
                    } else {
                        curWaitCount--;
                    }
                }
                boolean lifestealPlayerData = lifestealPlayerDataSavedBool.getState((IEntityDataSaver) player);
                boolean lifestealBanned = lifestealBannedBool.getState((IEntityDataSaver) player);
                boolean lifestealForceRefresh = lifestealForceRefreshBool.getState((IEntityDataSaver) player);
                float lifestealHealAmount = lifestealHealCountFloat.getState((IEntityDataSaver) player);
                if (!lifestealPlayerData) {
                    lifestealPlayerDataSavedBool.set((IEntityDataSaver) player, true);
                    lifestealHeartCountFloat.set((IEntityDataSaver) player, player.getMaxHealth());
                    lifestealBannedBool.set((IEntityDataSaver) player, false);
                    lifestealForceRefreshBool.set((IEntityDataSaver) player, false);
                    lifestealHealCountFloat.set((IEntityDataSaver) player, 0);
                }
                if (lifestealForceRefresh) {
                    lifestealForceRefreshBool.set((IEntityDataSaver) player, false);

                    EntityAttributeInstance attributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
                    EntityAttributeModifier modifier = new EntityAttributeModifier(Identifier.of( Lifesteal.MOD_ID + ":health_modifier"), 0, EntityAttributeModifier.Operation.ADD_VALUE);

                    assert attributeInstance != null;
                    attributeInstance.updateModifier(modifier);
                }
                float lifestealHearts = lifestealHeartCountFloat.getState((IEntityDataSaver) player);

                float healthDifference = 0;
                if (lifestealHearts != player.getMaxHealth()) {
                    if (lifestealHearts > minHealth) {
                        healthDifference = lifestealHearts - player.getMaxHealth();

                        EntityAttributeInstance attributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
                        EntityAttributeModifier modifier = new EntityAttributeModifier(Identifier.of( Lifesteal.MOD_ID + ":health_modifier"), healthDifference, EntityAttributeModifier.Operation.ADD_VALUE);

                        assert attributeInstance != null;
                        attributeInstance.updateModifier(modifier);
                    }
                }
                if (lifestealHealAmount != 0) {
                    if (lifestealHealAmount > 0) {
                        player.heal(lifestealHealAmount);
                    }
                    lifestealHealCountFloat.decrement((IEntityDataSaver) player, lifestealHealAmount);
                }
            }
        });
    }
}
