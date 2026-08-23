package com.lndydx.beyondnetherite.entity;

import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import com.lndydx.beyondnetherite.item.ModItems;

public class Shade extends Monster {
    private int lungeCooldown = 0;
    private boolean isStrafing = false;
    private int strafeTimer = 0;

    public Shade(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
        ItemStack sword = new ItemStack(ModItems.OBSIDIAN_SWORD);
        enchantSwordRandomly(sword, level);
        this.setItemSlot(EquipmentSlot.MAINHAND, sword);
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.xpReward = 50;
    }

    private void enchantSwordRandomly(ItemStack sword, Level level) {
        if (level.isClientSide()) return;

        RandomSource random = this.random;
        ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);

        var registry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> sharpnessOpt = registry.get(Enchantments.SHARPNESS);
        Optional<Holder.Reference<Enchantment>> fireAspectOpt = registry.get(Enchantments.FIRE_ASPECT);

        boolean hasSharpness = random.nextBoolean();
        boolean hasFireAspect = random.nextBoolean();

        if (!hasSharpness && !hasFireAspect) {
            hasSharpness = true;
        }

        if (hasSharpness && sharpnessOpt.isPresent()) {
            enchantments.set(sharpnessOpt.get(), random.nextInt(2) + 1);
        }
        if (hasFireAspect && fireAspectOpt.isPresent()) {
            enchantments.set(fireAspectOpt.get(), random.nextInt(2) + 1);
        }

        sword.set(DataComponents.ENCHANTMENTS, enchantments.toImmutable());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new ShadeCombatGoal(this));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 50.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.lungeCooldown > 0) this.lungeCooldown--;

        LivingEntity target = this.getTarget();
        if (target != null) {
            double dist = this.distanceTo(target);
            if (dist <= 10.0D && !this.isSprinting()) {
                this.setSprinting(true);
            } else if (dist > 10.0D && this.isSprinting()) {
                this.setSprinting(false);
            }
        } else if (this.isSprinting()) {
            this.setSprinting(false);
        }

        if (this.isStrafing) {
            this.strafeTimer--;
            if (this.strafeTimer <= 0) {
                this.isStrafing = false;
            }
        } else if (target != null && this.random.nextFloat() < 0.05F) {
            this.isStrafing = true;
            this.strafeTimer = 20 + this.random.nextInt(20);
        }
    }

    public boolean isStrafing() {
        return this.isStrafing;
    }

    public int getLungeCooldown() {
        return this.lungeCooldown;
    }

    public void setLungeCooldown(int ticks) {
        this.lungeCooldown = ticks;
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        this.swing(InteractionHand.MAIN_HAND);

        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);

        int sharp = 0;
        int fire = 0;
        ItemEnchantments ench = this.getMainHandItem().get(DataComponents.ENCHANTMENTS);
        if (ench != null) {
            var registry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            Optional<Holder.Reference<Enchantment>> sharpOpt = registry.get(Enchantments.SHARPNESS);
            Optional<Holder.Reference<Enchantment>> fireOpt = registry.get(Enchantments.FIRE_ASPECT);
            if (sharpOpt.isPresent()) sharp = ench.getLevel(sharpOpt.get());
            if (fireOpt.isPresent()) fire = ench.getLevel(fireOpt.get());
        }
        damage += sharp > 0 ? 0.5F + 0.5F * sharp : 0F;

        boolean crit = !this.onGround() && this.getDeltaMovement().y < 0.0D;
        if (crit) damage *= 1.3F;

        boolean success = target.hurtServer(level, this.damageSources().mobAttack(this), damage);
        if (success && fire > 0) {
            target.setRemainingFireTicks(fire * 4 * 20);
        }
        return success;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.DROWN)) return false;
        return super.hurtServer(level, source, amount);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }
}