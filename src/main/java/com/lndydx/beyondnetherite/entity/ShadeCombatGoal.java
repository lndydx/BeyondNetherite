package com.lndydx.beyondnetherite.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ShadeCombatGoal extends Goal {
    private final Shade shade;
    private LivingEntity target;
    private int attackCooldown = 0;
    private int flankTimer = 0;
    private double flankDir = 1.0D;

    public ShadeCombatGoal(Shade shade) {
        this.shade = shade;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity t = this.shade.getTarget();
        if (t != null && t.isAlive()) {
            this.target = t;
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.target != null && this.target.isAlive() && this.shade.getTarget() == this.target;
    }

    @Override
    public void stop() {
        this.target = null;
        this.shade.getNavigation().stop();
        this.flankTimer = 0;
    }

    @Override
    public void tick() {
        if (this.attackCooldown > 0) this.attackCooldown--;
        this.shade.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

        double dist = this.shade.distanceTo(this.target);

        // Detect shield
        boolean targetBlocking = this.target.isBlocking();
        boolean behind = false;
        if (targetBlocking) {
            Vec3 look = this.target.getLookAngle();
            double tx = this.shade.getX() - this.target.getX();
            double tz = this.shade.getZ() - this.target.getZ();
            double tl = Math.sqrt(tx * tx + tz * tz);
            if (tl > 0.001D) {
                double dot = (look.x * tx + look.z * tz) / tl;
                behind = dot < 0.4D;
            }
        }

        if (targetBlocking && !behind) {
            if (this.flankTimer <= 0) {
                this.flankTimer = 25 + this.shade.getRandom().nextInt(15);
                this.flankDir = this.shade.getRandom().nextBoolean() ? 1.0D : -1.0D;
            }
        } else {
            this.flankTimer = 0;
        }

        if (this.flankTimer > 0) {
            this.flankTimer--;
            double dx = this.target.getX() - this.shade.getX();
            double dz = this.target.getZ() - this.shade.getZ();
            double h = Math.max(Math.sqrt(dx * dx + dz * dz), 0.001D);
            double sx = (-dz / h) * this.flankDir * 0.35;
            double sz = (dx / h) * this.flankDir * 0.35D;
            double fx = (dx / h) * 0.25D;
            double fz = (dz / h) * 0.25D;
            this.shade.getNavigation().stop();
            this.shade.setDeltaMovement(this.shade.getDeltaMovement().add(sx + fx, 0.0D, sz + fz));
            return;
        }

        // Normal Attack
        if (this.attackCooldown <= 0 && dist <= 2.5D
                && this.shade.level() instanceof ServerLevel serverLevel) {
            this.shade.getNavigation().stop();
            this.shade.doHurtTarget(serverLevel, this.target);
            this.attackCooldown = 20;
        }

        // Lunge
        if (dist >= 5.0D && dist <= 10.0D && this.shade.getLungeCooldown() <= 0) {
            double dx = this.target.getX() - this.shade.getX();
            double dz = this.target.getZ() - this.shade.getZ();
            double h = Math.sqrt(dx * dx + dz * dz);
            if (h > 0) {
                this.shade.setDeltaMovement(this.shade.getDeltaMovement()
                        .add((dx / h) * 1.4D, 0.8D, (dz / h) * 1.4D));
                this.shade.setLungeCooldown(40 + this.shade.getRandom().nextInt(20));
            }
        }

        // Strafe
        if (this.shade.isStrafing() && dist > 2.5D) {
            double dx = this.target.getX() - this.shade.getX();
            double dz = this.target.getZ() - this.shade.getZ();
            double h = Math.sqrt(dx * dx + dz * dz);
            if (h > 0) {
                double dir = this.shade.getRandom().nextBoolean() ? 1.0D : -1.0D;
                this.shade.setDeltaMovement(this.shade.getDeltaMovement()
                        .add((-dz / h) * dir * 0.3D, 0.0D, (dx / h) * dir * 0.2D));
            }
        }

        if (dist > 1.8D) {
            this.shade.getNavigation().moveTo(this.target, 1.0D);
        } else {
            this.shade.getNavigation().stop();
        }
    }
}