package com.lndydx.beyondnetherite.item;

import java.util.IdentityHashMap;
import java.util.Map;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class ObsidianSetBonus {
    private static final Map<LivingEntity, Vec3> SAVED_MOTION = new IdentityHashMap<>();

    public static boolean hasFullSet(LivingEntity entity) {
        ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = entity.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = entity.getItemBySlot(EquipmentSlot.FEET);

        return head.is(ModItems.OBSIDIAN_HELMET)
                && (chest.is(ModItems.OBSIDIAN_CHESTPLATE) || chest.is(ModItems.WINGED_OBSIDIAN_CHESTPLATE))
                && legs.is(ModItems.OBSIDIAN_LEGGINGS)
                && feet.is(ModItems.OBSIDIAN_BOOTS);
    }

    public static void saveMotion(LivingEntity entity) {
        SAVED_MOTION.put(entity, entity.getDeltaMovement());
    }

    public static void restoreMotion() {
        SAVED_MOTION.forEach((entity, motion) -> entity.setDeltaMovement(motion));
        SAVED_MOTION.clear();
    }
}