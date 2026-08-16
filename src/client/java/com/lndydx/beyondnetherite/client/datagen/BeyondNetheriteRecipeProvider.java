package com.lndydx.beyondnetherite.client.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import com.lndydx.beyondnetherite.block.ModBlocks;
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

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DENSE_OBSIDIAN_ITEM, 1)
                        .pattern("OOO")
                        .pattern("OOO")
                        .pattern("OOO")
                        .define('O', Blocks.OBSIDIAN)
                        .unlockedBy(getHasName(Blocks.OBSIDIAN), has(Blocks.OBSIDIAN))
                        .save(exporter);

                obsidianSmithing(this, Items.NETHERITE_SWORD, RecipeCategory.COMBAT, ModItems.OBSIDIAN_SWORD);
                obsidianSmithing(this, Items.NETHERITE_AXE, RecipeCategory.TOOLS, ModItems.OBSIDIAN_AXE);
                obsidianSmithing(this, Items.NETHERITE_PICKAXE, RecipeCategory.TOOLS, ModItems.OBSIDIAN_PICKAXE);
                obsidianSmithing(this, Items.NETHERITE_SHOVEL, RecipeCategory.TOOLS, ModItems.OBSIDIAN_SHOVEL);
                obsidianSmithing(this, Items.NETHERITE_HOE, RecipeCategory.TOOLS, ModItems.OBSIDIAN_HOE);
                obsidianSmithing(this, Items.NETHERITE_SPEAR, RecipeCategory.COMBAT, ModItems.OBSIDIAN_SPEAR);
                obsidianSmithing(this, Items.NETHERITE_HELMET, RecipeCategory.COMBAT, ModItems.OBSIDIAN_HELMET);
                obsidianSmithing(this, Items.NETHERITE_CHESTPLATE, RecipeCategory.COMBAT, ModItems.OBSIDIAN_CHESTPLATE);
                obsidianSmithing(this, Items.NETHERITE_LEGGINGS, RecipeCategory.COMBAT, ModItems.OBSIDIAN_LEGGINGS);
                obsidianSmithing(this, Items.NETHERITE_BOOTS, RecipeCategory.COMBAT, ModItems.OBSIDIAN_BOOTS);
            }

            private void obsidianSmithing(RecipeProvider ctx, Item base, RecipeCategory category, Item result) {
                SmithingTransformRecipeBuilder.smithing(
                                Ingredient.of(ModItems.OBSIDIAN_SMITHING_TEMPLATE),
                                Ingredient.of(base),
                                Ingredient.of(ModItems.OBSIDIAN_ALLOY),
                                category,
                                result
                        )
                        .unlocks(getHasName(ModItems.OBSIDIAN_ALLOY), has(ModItems.OBSIDIAN_ALLOY))
                        .save(exporter, getItemName(result) + "_smithing");
            }
        };
    }

    @Override
    public String getName() {
        return "Beyond Netherite Recipes";
    }
}