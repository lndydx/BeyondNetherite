package com.lndydx.beyondnetherite.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import com.lndydx.beyondnetherite.BeyondNetherite;

public class ModItemIds {
    public static final ResourceKey<Item> OBSIDIAN_SHARD = key("obsidian_shard");
    public static final ResourceKey<Item> OBSIDIAN_ALLOY = key("obsidian_alloy");
    public static final ResourceKey<Item> OBSIDIAN_SMITHING_TEMPLATE = key("obsidian_smithing_template");
    public static final ResourceKey<Item> OBSIDIAN_SWORD = key("obsidian_sword");
    public static final ResourceKey<Item> OBSIDIAN_AXE = key("obsidian_axe");
    public static final ResourceKey<Item> OBSIDIAN_PICKAXE = key("obsidian_pickaxe");
    public static final ResourceKey<Item> OBSIDIAN_SHOVEL = key("obsidian_shovel");
    public static final ResourceKey<Item> OBSIDIAN_HOE = key("obsidian_hoe");
    public static final ResourceKey<Item> OBSIDIAN_SPEAR = key("obsidian_spear");
    public static final ResourceKey<Item> OBSIDIAN_HELMET = key("obsidian_helmet");
    public static final ResourceKey<Item> OBSIDIAN_CHESTPLATE = key("obsidian_chestplate");
    public static final ResourceKey<Item> OBSIDIAN_LEGGINGS = key("obsidian_leggings");
    public static final ResourceKey<Item> OBSIDIAN_BOOTS = key("obsidian_boots");
    public static final ResourceKey<Item> OBSIDIAN_ARROW = key("obsidian_arrow");
    public static final ResourceKey<Item> WINGED_NETHERITE_CHESTPLATE = key("winged_netherite_chestplate");
    public static final ResourceKey<Item> WINGED_OBSIDIAN_CHESTPLATE = key("winged_obsidian_chestplate");
    public static final ResourceKey<Item> OBSIDIAN_GOLEM_SPAWN_EGG = key("obsidian_golem_spawn_egg");
    public static final ResourceKey<Item> SHADE_SPAWN_EGG = key("shade_spawn_egg");

    private static ResourceKey<Item> key(String path) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, path));
    }
}