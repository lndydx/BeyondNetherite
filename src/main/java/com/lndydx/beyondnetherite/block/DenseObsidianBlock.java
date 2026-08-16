package com.lndydx.beyondnetherite.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import com.lndydx.beyondnetherite.entity.ModEntities;

import org.jetbrains.annotations.Nullable;

public class DenseObsidianBlock extends Block {
    public DenseObsidianBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide()) {
            trySpawnGolem(level, pos);
        }
    }

    private void trySpawnGolem(Level level, BlockPos headPos) {
        BlockPos center = headPos.below();
        BlockPos leg = center.below();

        for (Direction dir : new Direction[]{Direction.NORTH, Direction.EAST}) {
            BlockPos left = center.relative(dir);
            BlockPos right = center.relative(dir.getOpposite());

            if (isDense(level, center) && isDense(level, leg) && isDense(level, left) && isDense(level, right)) {
                level.setBlock(headPos, Blocks.AIR.defaultBlockState(), 3);
                level.setBlock(center, Blocks.AIR.defaultBlockState(), 3);
                level.setBlock(leg, Blocks.AIR.defaultBlockState(), 3);
                level.setBlock(left, Blocks.AIR.defaultBlockState(), 3);
                level.setBlock(right, Blocks.AIR.defaultBlockState(), 3);
                ModEntities.OBSIDIAN_GOLEM.spawn((ServerLevel) level, center, EntitySpawnReason.STRUCTURE);
                return;
            }
        }
    }

    private static boolean isDense(Level level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() == ModBlocks.DENSE_OBSIDIAN;
    }
}