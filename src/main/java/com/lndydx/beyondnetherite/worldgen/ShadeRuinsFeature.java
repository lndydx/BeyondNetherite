package com.lndydx.beyondnetherite.worldgen;

import com.lndydx.beyondnetherite.BeyondNetherite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class ShadeRuinsFeature extends Feature<NoneFeatureConfiguration> {
    private static final TagKey<Biome> IS_RIVER = TagKey.create(Registries.BIOME, Identifier.parse("minecraft:is_river"));
    private static final TagKey<Biome> IS_OCEAN = TagKey.create(Registries.BIOME, Identifier.parse("minecraft:is_ocean"));
    private static final TagKey<Biome> IS_BEACH = TagKey.create(Registries.BIOME, Identifier.parse("minecraft:is_beach"));

    public ShadeRuinsFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        if (context.random().nextInt(100) != 0) {
            return false;
        }

        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();

        int x0 = origin.getX() - 12;
        int z0 = origin.getZ() - 11;
        int x1 = x0 + 23;
        int z1 = z0 + 21;

        int[][] points = {
                {x0, z0}, {x1, z0}, {x0, z1}, {x1, z1},
                {(x0 + x1) / 2, (z0 + z1) / 2}
        };

        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (int[] p : points) {
            int y = level.getHeight(Heightmap.Types.WORLD_SURFACE, p[0], p[1]);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
            Holder<Biome> biome = level.getBiome(new BlockPos(p[0], y, p[1]));
            if (biome.is(IS_RIVER) || biome.is(IS_OCEAN) || biome.is(IS_BEACH)) {
                return false;
            }
        }

        if (maxY - minY > 2) {
            return false;
        }

        ServerLevel serverLevel = (ServerLevel) level.getLevel();
        StructureTemplate template = serverLevel.getStructureManager()
                .getOrCreate(BeyondNetherite.id("shade_ruins"));

        StructurePlaceSettings settings = new StructurePlaceSettings();
        settings.setRandom(context.random());

        BlockPos pos = new BlockPos(x0, maxY, z0);
        return template.placeInWorld(level, pos, pos, settings, context.random(), 2);
    }
}