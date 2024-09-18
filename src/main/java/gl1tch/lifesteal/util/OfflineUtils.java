package gl1tch.lifesteal.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.c2s.common.SyncedClientOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class OfflineUtils {
    public static Optional<ServerPlayerEntity> getPlayer(MinecraftServer server, UUID uuid) {
        Optional<ServerPlayerEntity> player = Optional.ofNullable(server.getPlayerManager().getPlayer(uuid));
        if (player.isPresent()) {
            return player;
        } else {
            return getOfflinePlayer(server, uuid);
        }
    }

    public static Optional<ServerPlayerEntity> getOfflinePlayer(MinecraftServer server, UUID uuid) {
        Optional<GameProfile> profile = Objects.requireNonNull(server.getUserCache()).getByUuid(uuid);
        return profile.map(gameProfile -> server.getPlayerManager().createPlayer(gameProfile, SyncedClientOptions.createDefault()));
    }

    public static Optional<NbtCompound> getPlayerData(MinecraftServer server, ServerPlayerEntity player) {
        return server.getPlayerManager().loadPlayerData(player);
    }

    public static void savePlayerData(ServerPlayerEntity player, NbtCompound player_data, MinecraftServer server) {
        player.saveNbt(player_data);
        player.remove(Entity.RemovalReason.DISCARDED);
        server.getPlayerManager().remove(player);
    }

    public static boolean isPlayerOnline(ServerPlayerEntity player, MinecraftServer server) {
        return server.getPlayerManager().getPlayerList().contains(player);
    }

    public static void teleportOffline(ServerPlayerEntity player, Vec3d pos) {
        player.setPosition(pos);
    }

    public static void setDimension(NbtCompound player_data, ServerWorld dimension) {
        player_data.putString("Dimension", dimension.getRegistryKey().getValue().toString());
    }

//    public static void setGameMode(NbtCompound player_data, GameMode mode) {
//        player_data.putInt("playerGameType", mode.getId());
//    }

//    public static GameMode getGameMode(NbtCompound player_data) {
//        return GameMode.byId(player_data.getInt("playerGameType"));
//    }
}