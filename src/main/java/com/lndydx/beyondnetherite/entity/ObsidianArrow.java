package com.lndydx.beyondnetherite.entity;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import com.lndydx.beyondnetherite.item.ModItems;

public class ObsidianArrow extends AbstractArrow {
    private static final Identifier TOUGHNESS_IGNORE_ID = Identifier.fromNamespaceAndPath("beyond-netherite", "obsidian_arrow_toughness_ignore");

    public ObsidianArrow(EntityType<? extends ObsidianArrow> type, Level level) {
        super(type, level);
        setBaseDamage(4.0D);
    }

    public ObsidianArrow(Level level, LivingEntity shooter, ItemStack pickupStack, ItemStack firedFromWeapon) {
        super(ModEntities.OBSIDIAN_ARROW, shooter, level, pickupStack, firedFromWeapon);
        setBaseDamage(4.0D);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.OBSIDIAN_ARROW);
    }

    @Override
    public byte getPierceLevel() {
        return (byte) (super.getPierceLevel() + 1);
    }

    @Override
    public void onHitEntity(EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();
        AttributeInstance toughness = target instanceof LivingEntity living ? living.getAttribute(Attributes.ARMOR_TOUGHNESS) : null;
        AttributeModifier mod = null;
        if (toughness != null) {
            mod = new AttributeModifier(TOUGHNESS_IGNORE_ID, -0.15D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            toughness.addTransientModifier(mod);
        }
        try {
            super.onHitEntity(hitResult);
        } finally {
            if (toughness != null && mod != null) {
                toughness.removeModifier(mod);
            }
        }
    }
}