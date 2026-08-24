package com.lndydx.beyondnetherite.arena;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.border.WorldBorder;

import com.lndydx.beyondnetherite.BeyondNetherite;
import com.lndydx.beyondnetherite.entity.Shade;

public class ShadeArenaManager {
    private static final Map<ServerLevel, ShadeArena> activeArenas = new HashMap<>();
    private static final Set<UUID> lockedPlayers = new HashSet<>();

    public static ShadeArena createArena(BlockPos center, ServerLevel level, Shade shade) {
        WorldBorder wb = level.getWorldBorder();
        ShadeArena arena = new ShadeArena(center, level, shade,
                wb.getCenterX(), wb.getCenterZ(), wb.getSize());
        activeArenas.put(level, arena);

        wb.setCenter(arena.getCenterX(), arena.getCenterZ());
        wb.setSize(arena.getRadius() * 2.0D);

        forceChunks(level, center, true);

        for (Player p : level.players()) {
            if (!p.isCreative() && !p.isSpectator() && arena.isInside(p.position())) {
                lockedPlayers.add(p.getUUID());
            }
        }
        BeyondNetherite.LOGGER.info("[BN] Arena CREATED at {}", center);
        return arena;
    }

    public static void destroyArena(ServerLevel level) {
        BeyondNetherite.LOGGER.info("[BN] Arena DESTROY in {}", level.dimension().identifier());
        lockedPlayers.clear();
        ShadeArena arena = activeArenas.remove(level);
        if (arena != null) {
            forceChunks(arena.getLevel(), arena.getCenter(), false);
            WorldBorder wb = level.getWorldBorder();
            wb.setCenter(arena.getOldCenterX(), arena.getOldCenterZ());
            wb.setSize(arena.getOldSize());
        }
    }

    public static ShadeArena getArena(ServerLevel level) {
        return activeArenas.get(level);
    }

    public static boolean isLocked(Player player) {
        return lockedPlayers.contains(player.getUUID());
    }

    public static boolean shouldRestrictPlayer(Player player) {
        if (player.isCreative() || player.isSpectator()) return false;
        return lockedPlayers.contains(player.getUUID());
    }

    public static void tick(ServerLevel level) {
        ShadeArena arena = getArena(level);
        if (arena == null) return;

        Shade shade = arena.getShade();

        if (!shade.isAlive()) {
            BeyondNetherite.LOGGER.info("[BN] Shade dead via tick check, destroying arena");
            destroyArena(level);
            return;
        }

        WorldBorder wb = level.getWorldBorder();
        double targetSize = arena.getRadius() * 2.0D;
        double targetX = arena.getCenterX();
        double targetZ = arena.getCenterZ();
        if (Math.abs(wb.getSize() - targetSize) > 0.01D
                || Math.abs(wb.getCenterX() - targetX) > 0.01D
                || Math.abs(wb.getCenterZ() - targetZ) > 0.01D) {
            wb.setCenter(targetX, targetZ);
            wb.setSize(targetSize);
        }

        for (Player p : level.players()) {
            if (p.isCreative() || p.isSpectator()) continue;

            if (!lockedPlayers.contains(p.getUUID()) && arena.isInside(p.position())) {
                lockedPlayers.add(p.getUUID());
            }

            if (lockedPlayers.contains(p.getUUID())) {
                double dx = Math.abs(p.getX() - targetX);
                double dz = Math.abs(p.getZ() - targetZ);
                if (dx > arena.getRadius() + 2.0D || dz > arena.getRadius() + 2.0D) {
                    p.teleportTo(targetX, arena.getCenter().getY() + 1.0, targetZ);
                    p.setDeltaMovement(net.minecraft.world.phys.Vec3.ZERO);
                }
            }
        }

        double sx = Math.abs(shade.getX() - targetX);
        double sz = Math.abs(shade.getZ() - targetZ);
        if (sx > arena.getRadius() + 2.0D || sz > arena.getRadius() + 2.0D) {
            shade.teleportTo(targetX, shade.getY(), targetZ);
            shade.setDeltaMovement(net.minecraft.world.phys.Vec3.ZERO);
        }
    }

    private static void forceChunks(ServerLevel level, BlockPos center, boolean forced) {
        int cx = center.getX() >> 4;
        int cz = center.getZ() >> 4;
        for (int x = cx - 1; x <= cx + 1; x++) {
            for (int z = cz - 1; z <= cz + 1; z++) {
                level.setChunkForced(x, z, forced);
            }
        }
    }
}