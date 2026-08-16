package com.lndydx.beyondnetherite.client.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import com.lndydx.beyondnetherite.block.ModBlocks;

public class BeyondNetheriteBlockTagProvider extends TagsProvider<Block> {
    public BeyondNetheriteBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BLOCK, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ResourceKey<Block> denseKey = ResourceKey.create(Registries.BLOCK, BuiltInRegistries.BLOCK.getKey(ModBlocks.DENSE_OBSIDIAN));
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(denseKey);
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(denseKey);
    }
}