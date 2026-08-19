package com.lndydx.beyondnetherite.item;

import java.util.Map;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.tags.ItemTags;
import com.lndydx.beyondnetherite.BeyondNetherite;

public class ModArmorMaterials {
    public static final ResourceKey<EquipmentAsset> OBSIDIAN_ASSET = ResourceKey.create(
            EquipmentAssets.ROOT_ID,
            Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, "obsidian"));

    public static final ResourceKey<EquipmentAsset> WINGED_OBSIDIAN_ASSET = ResourceKey.create(
            EquipmentAssets.ROOT_ID,
            Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, "winged_obsidian_chestplate"));

    public static final ResourceKey<EquipmentAsset> WINGED_NETHERITE_ASSET = ResourceKey.create(
            EquipmentAssets.ROOT_ID,
            Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, "winged_netherite_chestplate"));

    private static final TagKey<Item> OBSIDIAN_ALLOY_TAG = TagKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, "obsidian_alloy"));

    public static final ArmorMaterial OBSIDIAN = new ArmorMaterial(
            46,
            Map.of(ArmorType.HELMET, 3, ArmorType.CHESTPLATE, 8, ArmorType.LEGGINGS, 6, ArmorType.BOOTS, 3),
            15, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0F, 0.15F,
            OBSIDIAN_ALLOY_TAG, OBSIDIAN_ASSET);

    public static final ArmorMaterial WINGED_OBSIDIAN = new ArmorMaterial(
            46,
            Map.of(ArmorType.HELMET, 3, ArmorType.CHESTPLATE, 8, ArmorType.LEGGINGS, 6, ArmorType.BOOTS, 3),
            15, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0F, 0.15F,
            OBSIDIAN_ALLOY_TAG, WINGED_OBSIDIAN_ASSET);

    public static final ArmorMaterial WINGED_NETHERITE = new ArmorMaterial(
            37,
            Map.of(ArmorType.HELMET, 3, ArmorType.CHESTPLATE, 8, ArmorType.LEGGINGS, 6, ArmorType.BOOTS, 3),
            15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.10F,
            TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, "winged_netherite_repair")),
            WINGED_NETHERITE_ASSET);

    public static void initialize() {
    }
}