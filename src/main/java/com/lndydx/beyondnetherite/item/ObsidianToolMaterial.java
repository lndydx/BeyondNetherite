package com.lndydx.beyondnetherite.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

import com.lndydx.beyondnetherite.BeyondNetherite;

public class ObsidianToolMaterial {
    public static final TagKey<Item> OBSIDIAN_ALLOY_TAG = TagKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, "obsidian_alloy")
    );

    public static final ToolMaterial INSTANCE = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            2539,
            9.0F,
            6.0F,
            15,
            OBSIDIAN_ALLOY_TAG
    );
}