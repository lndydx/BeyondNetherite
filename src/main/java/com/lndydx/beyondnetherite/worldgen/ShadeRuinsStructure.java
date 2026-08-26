package com.lndydx.beyondnetherite.worldgen;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Optional;

public class ShadeRuinsStructure extends Structure {
    public static final MapCodec<ShadeRuinsStructure> CODEC = simpleCodec(ShadeRuinsStructure::new);
    public static final StructureType<ShadeRuinsStructure> TYPE = () -> CODEC;

    public ShadeRuinsStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        int cx = chunkPos.getMiddleBlockX();
        int cz = chunkPos.getMiddleBlockZ();
        int x0 = cx - 12;
        int z0 = cz - 11;

        int[][] points = {
                {x0, z0}, {x0 + 23, z0}, {x0, z0 + 21}, {x0 + 23, z0 + 21}, {cx, cz}
        };
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (int[] p : points) {
            int surface = context.chunkGenerator().getFirstFreeHeight(
                    p[0], p[1], Heightmap.Types.WORLD_SURFACE_WG,
                    context.heightAccessor(), context.randomState());
            int ocean = context.chunkGenerator().getFirstFreeHeight(
                    p[0], p[1], Heightmap.Types.OCEAN_FLOOR_WG,
                    context.heightAccessor(), context.randomState());

            if (surface != ocean) {
                return Optional.empty();
            }

            minY = Math.min(minY, surface);
            maxY = Math.max(maxY, surface);
        }

        if (maxY - minY > 4) {
            return Optional.empty();
        }

        BlockPos pos = new BlockPos(x0, maxY, z0);
        return Optional.of(new GenerationStub(pos,
                builder -> builder.addPiece(new ShadeRuinsPiece(pos))));
    }

    @Override
    public StructureType<?> type() {
        return TYPE;
    }
}