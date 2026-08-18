package com.lndydx.beyondnetherite.client.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;

import com.lndydx.beyondnetherite.item.ModItemIds;

public class BeyondNetheriteItemTagProvider extends TagsProvider<net.minecraft.world.item.Item> {
    public BeyondNetheriteItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ITEM, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.ARROWS).add(ModItemIds.OBSIDIAN_ARROW);
    }
}