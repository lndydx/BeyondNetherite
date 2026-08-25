package com.lndydx.beyondnetherite.worldgen;

import com.lndydx.beyondnetherite.BeyondNetherite;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModFeatures {

    public static void initialize() {
        Registry.register(BuiltInRegistries.FEATURE,
                BeyondNetherite.id("shade_ruins_feature"), new ShadeRuinsFeature());

        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(
                        biome("plains"), biome("sunflower_plains"), biome("snowy_plains"),
                        biome("savanna"), biome("desert"),
                        biome("forest"), biome("flower_forest"), biome("birch_forest"),
                        biome("dark_forest"), biome("old_growth_birch_forest"),
                        biome("taiga"), biome("snowy_taiga")
                ),
                GenerationStep.Decoration.SURFACE_STRUCTURES,
                ResourceKey.create(Registries.PLACED_FEATURE, BeyondNetherite.id("shade_ruins"))
        );
    }

    private static ResourceKey<Biome> biome(String path) {
        return ResourceKey.create(Registries.BIOME, Identifier.parse("minecraft:" + path));
    }
}