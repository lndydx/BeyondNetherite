package com.lndydx.beyondnetherite.block;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import com.lndydx.beyondnetherite.BeyondNetherite;

public class ModBlocks {
    public static Block DENSE_OBSIDIAN;
    public static Item DENSE_OBSIDIAN_ITEM;

    private static ResourceKey<Block> blockKey(String path) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, path));
    }

    private static ResourceKey<Item> itemKey(String path) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, path));
    }

    public static void initialize() {
        ResourceKey<Block> denseKey = blockKey("dense_obsidian");

        DENSE_OBSIDIAN = Registry.register(
                BuiltInRegistries.BLOCK,
                denseKey,
                new DenseObsidianBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
                        .setId(denseKey)
                        .strength(50.0F, 2400.0F))
        );

        DENSE_OBSIDIAN_ITEM = Registry.register(
                BuiltInRegistries.ITEM,
                itemKey("dense_obsidian"),
                new BlockItem(DENSE_OBSIDIAN, new Item.Properties().setId(itemKey("dense_obsidian")).fireResistant())
        );

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS)
                .register((entries) -> entries.accept(DENSE_OBSIDIAN_ITEM));
    }
}