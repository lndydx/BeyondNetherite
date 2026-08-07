package com.lndydx.beyondnetherite.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import com.lndydx.beyondnetherite.entity.ObsidianArrow;

public class ObsidianArrowItem extends ArrowItem {
    public ObsidianArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity owner, ItemStack firedFromWeapon) {
        return new ObsidianArrow(level, owner, itemStack, firedFromWeapon);
    }
}