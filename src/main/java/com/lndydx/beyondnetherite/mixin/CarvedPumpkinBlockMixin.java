package com.lndydx.beyondnetherite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;

import com.lndydx.beyondnetherite.BeyondNetherite;
import com.lndydx.beyondnetherite.block.ModBlocks;
import com.lndydx.beyondnetherite.entity.ModEntities;

@Mixin(CarvedPumpkinBlock.class)
public abstract class CarvedPumpkinBlockMixin {
    @Inject(method = "trySpawnGolem", at = @At("TAIL"))
    private void beyondnetherite$tryObsidianGolem(Level level, BlockPos pos, CallbackInfo ci) {
        if (level.isClientSide()) {
            return;
        }

        BeyondNetherite.LOGGER.info("[BN] Carved pumpkin placed at {}", pos);

        BlockPos center = pos.below();
        BlockPos leg = center.below();

        for (Direction dir : new Direction[]{Direction.NORTH, Direction.EAST}) {
            BlockPos left = center.relative(dir);
            BlockPos right = center.relative(dir.getOpposite());

            if (isDense(level, center) && isDense(level, leg) && isDense(level, left) && isDense(level, right)) {
                BeyondNetherite.LOGGER.info("[BN] Obsidian Golem pattern detected!");
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                level.setBlock(center, Blocks.AIR.defaultBlockState(), 3);
                level.setBlock(leg, Blocks.AIR.defaultBlockState(), 3);
                level.setBlock(left, Blocks.AIR.defaultBlockState(), 3);
                level.setBlock(right, Blocks.AIR.defaultBlockState(), 3);
                ModEntities.OBSIDIAN_GOLEM.spawn((ServerLevel) level, leg, EntitySpawnReason.STRUCTURE);
                return;
            }
        }
    }

    private static boolean isDense(Level level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() == ModBlocks.DENSE_OBSIDIAN;
    }
}