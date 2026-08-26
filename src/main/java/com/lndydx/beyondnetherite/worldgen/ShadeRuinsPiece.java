package com.lndydx.beyondnetherite.worldgen;

import com.lndydx.beyondnetherite.BeyondNetherite;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class ShadeRuinsPiece extends StructurePiece {
    private final BlockPos templatePos;

    public ShadeRuinsPiece(BlockPos templatePos) {
        super(ModStructures.SHADE_RUINS_PIECE, 0, new BoundingBox(
                templatePos.getX(), templatePos.getY() - 4, templatePos.getZ(),
                templatePos.getX() + 23, templatePos.getY() + 15, templatePos.getZ() + 21));
        this.templatePos = templatePos;
    }

    public ShadeRuinsPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(ModStructures.SHADE_RUINS_PIECE, tag);
        this.templatePos = new BlockPos(
                tag.getInt("TPX").orElse(0),
                tag.getInt("TPY").orElse(0),
                tag.getInt("TPZ").orElse(0));
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        tag.putInt("TPX", templatePos.getX());
        tag.putInt("TPY", templatePos.getY());
        tag.putInt("TPZ", templatePos.getZ());
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager structureManager,
                            ChunkGenerator generator, RandomSource random,
                            BoundingBox box, ChunkPos chunkPos, BlockPos blockPos) {
        StructureTemplateManager manager = level.getServer().getStructureManager();
        StructureTemplate template = manager.getOrCreate(BeyondNetherite.id("temple_of_shade"));

        StructurePlaceSettings settings = new StructurePlaceSettings();
        settings.setRandom(random);
        template.placeInWorld(level, templatePos, templatePos, settings, random, 2);

        for (int x = templatePos.getX(); x < templatePos.getX() + 24; x++) {
            for (int z = templatePos.getZ(); z < templatePos.getZ() + 22; z++) {
                for (int y = templatePos.getY() - 4; y < templatePos.getY(); y++) {
                    level.setBlock(new BlockPos(x, y, z),
                            foundationBlock(y - (templatePos.getY() - 4), random), 2);
                }
            }
        }
    }

    private BlockState foundationBlock(int layer, RandomSource random) {
        switch (layer) {
            case 0:
                return random.nextFloat() < 0.5f
                        ? Blocks.DEEPSLATE.defaultBlockState()
                        : Blocks.COBBLED_DEEPSLATE.defaultBlockState();
            case 1:
                return random.nextFloat() < 0.5f
                        ? Blocks.COBBLED_DEEPSLATE.defaultBlockState()
                        : Blocks.TUFF.defaultBlockState();
            case 2:
                return random.nextFloat() < 0.5f
                        ? Blocks.TUFF.defaultBlockState()
                        : Blocks.MOSSY_COBBLESTONE.defaultBlockState();
            default: {
                float f = random.nextFloat();
                if (f < 0.4f) return Blocks.MOSSY_COBBLESTONE.defaultBlockState();
                if (f < 0.75f) return Blocks.MOSS_BLOCK.defaultBlockState();
                return Blocks.PALE_MOSS_BLOCK.defaultBlockState();
            }
        }
    }
}