package com.lndydx.beyondnetherite.item;

import com.lndydx.beyondnetherite.BeyondNetherite;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItemIds {
    public static ResourceKey<Item> create(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, name));
    }

    public static final ResourceKey<Item> OBSIDIAN_SHARD = create("obsidian_shard");
    public static final ResourceKey<Item> OBSIDIAN_ALLOY = create("obsidian_alloy");
    public static final ResourceKey<Item> OBSIDIAN_SMITHING_TEMPLATE = create("obsidian_smithing_template");
    public static final ResourceKey<Item> OBSIDIAN_SWORD = create("obsidian_sword");
    public static final ResourceKey<Item> OBSIDIAN_AXE = create("obsidian_axe");
    public static final ResourceKey<Item> OBSIDIAN_PICKAXE = create("obsidian_pickaxe");
    public static final ResourceKey<Item> OBSIDIAN_SHOVEL = create("obsidian_shovel");
    public static final ResourceKey<Item> OBSIDIAN_HOE = create("obsidian_hoe");
    public static final ResourceKey<Item> OBSIDIAN_SPEAR = create("obsidian_spear");
    public static final ResourceKey<Item> OBSIDIAN_HELMET = create("obsidian_helmet");
    public static final ResourceKey<Item> OBSIDIAN_CHESTPLATE = create("obsidian_chestplate");
    public static final ResourceKey<Item> OBSIDIAN_LEGGINGS = create("obsidian_leggings");
    public static final ResourceKey<Item> OBSIDIAN_BOOTS = create("obsidian_boots");
    public static final ResourceKey<Item> OBSIDIAN_ARROW = create("obsidian_arrow");
    public static final ResourceKey<Item> WINGED_NETHERITE_CHESTPLATE = create("winged_netherite_chestplate");
    public static final ResourceKey<Item> WINGED_OBSIDIAN_CHESTPLATE = create("winged_obsidian_chestplate");
}