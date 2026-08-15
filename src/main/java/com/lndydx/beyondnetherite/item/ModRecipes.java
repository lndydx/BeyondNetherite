package com.lndydx.beyondnetherite.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

import com.lndydx.beyondnetherite.BeyondNetherite;

public class ModRecipes {
    public static final RecipeSerializer<WingedChestplateRecipe> WINGED_CHESTPLATE = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            BeyondNetherite.id("winged_chestplate"),
            new RecipeSerializer<>(
                    MapCodec.unit(new WingedChestplateRecipe()),
                    StreamCodec.unit(new WingedChestplateRecipe())));

    public static void initialize() {
    }
}