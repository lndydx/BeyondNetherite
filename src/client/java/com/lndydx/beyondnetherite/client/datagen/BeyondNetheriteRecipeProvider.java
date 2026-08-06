package com.lndydx.beyondnetherite.client.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;

import com.lndydx.beyondnetherite.item.ModItems;

public class BeyondNetheriteRecipeProvider extends FabricRecipeProvider {
    public BeyondNetheriteRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                shapeless(RecipeCategory.MISC, ModItems.OBSIDIAN_SHARD, 4)
                        .requires(Items.OBSIDIAN)
                        .unlockedBy(getHasName(Items.OBSIDIAN), has(Items.OBSIDIAN))
                        .save(exporter);

                shaped(RecipeCategory.MISC, ModItems.OBSIDIAN_ALLOY, 1)
                        .pattern("NON")
                        .pattern("ONO")
                        .pattern("NON")
                        .define('N', Items.NETHERITE_SCRAP)
                        .define('O', Items.OBSIDIAN)
                        .unlockedBy(getHasName(Items.NETHERITE_SCRAP), has(Items.NETHERITE_SCRAP))
                        .save(exporter);

                shaped(RecipeCategory.COMBAT, ModItems.OBSIDIAN_ARROW, 2)
                        .pattern("O")
                        .pattern("S")
                        .pattern("F")
                        .define('O', ModItems.OBSIDIAN_SHARD)
                        .define('S', Items.STICK)
                        .define('F', Items.FEATHER)
                        .unlockedBy(getHasName(ModItems.OBSIDIAN_SHARD), has(ModItems.OBSIDIAN_SHARD))
                        .save(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "Beyond Netherite Recipes";
    }
}