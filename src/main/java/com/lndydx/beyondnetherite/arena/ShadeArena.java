package com.lndydx.beyondnetherite.arena;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

import com.lndydx.beyondnetherite.entity.Shade;

public class ShadeArena {
    private final BlockPos center;
    private final double centerX;
    private final double centerZ;
    private final ServerLevel level;
    private final Shade shade;
    private final double radius;
    private final double oldCenterX;
    private final double oldCenterZ;
    private final double oldSize;

    public ShadeArena(BlockPos center, ServerLevel level, Shade shade,
                      double oldCenterX, double oldCenterZ, double oldSize) {
        this.center = center;
        this.centerX = center.getX() + 0.5;
        this.centerZ = center.getZ() + 0.5;
        this.level = level;
        this.shade = shade;
        this.radius = 13.0D;
        this.oldCenterX = oldCenterX;
        this.oldCenterZ = oldCenterZ;
        this.oldSize = oldSize;
    }

    public BlockPos getCenter() { return this.center; }
    public double getCenterX() { return this.centerX; }
    public double getCenterZ() { return this.centerZ; }
    public ServerLevel getLevel() { return this.level; }
    public Shade getShade() { return this.shade; }
    public double getRadius() { return this.radius; }
    public double getOldCenterX() { return this.oldCenterX; }
    public double getOldCenterZ() { return this.oldCenterZ; }
    public double getOldSize() { return this.oldSize; }

    public boolean isInside(Vec3 pos) {
        return Math.abs(pos.x - centerX) <= radius && Math.abs(pos.z - centerZ) <= radius;
    }

    public Vec3 clampPosition(Vec3 pos) {
        double x = Math.max(centerX - radius, Math.min(centerX + radius, pos.x));
        double z = Math.max(centerZ - radius, Math.min(centerZ + radius, pos.z));
        return new Vec3(x, pos.y, z);
    }

    public boolean isShadeAlive() {
        return this.shade.isAlive();
    }
}