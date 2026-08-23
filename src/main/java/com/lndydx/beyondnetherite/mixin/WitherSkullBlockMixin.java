package com.lndydx.beyondnetherite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WitherSkullBlock;
import net.minecraft.world.phys.AABB;

import com.lndydx.beyondnetherite.block.ModBlocks;
import com.lndydx.beyondnetherite.entity.ModEntities;
import com.lndydx.beyondnetherite.entity.Shade;

@Mixin(WitherSkullBlock.class)
public abstract class WitherSkullBlockMixin {
    @Inject(method = "checkSpawn", at = @At("TAIL"))
    private static void beyondnetherite$tryShadeRitual(Level level, BlockPos pos, CallbackInfo ci) {
        if (level.isClientSide()) return;
        ServerLevel serverLevel = (ServerLevel) level;

        BlockPos below = pos.below();
        BlockPos below2 = below.below();
        if (!isDense(level, below) || !isDense(level, below2)) return;

        AABB searchBox = new AABB(pos).inflate(10000.0D);
        if (!level.getEntitiesOfClass(Shade.class, searchBox, e -> e.isAlive()).isEmpty()) return;

        // Consume skull + 2 dense obsidian
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        level.setBlock(below, Blocks.AIR.defaultBlockState(), 3);
        level.setBlock(below2, Blocks.AIR.defaultBlockState(), 3);

        level.playSound(null, pos, SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 1.0F, 1.0F);
        serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, 0, 0, 0, 0);
        for (int i = 0; i < 30; i++) {
            serverLevel.sendParticles(ParticleTypes.SMOKE,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, 0.3, 0.3, 0.3, 0.02);
        }

        // Spawn Shade
        Shade shade = new Shade(ModEntities.SHADE, serverLevel);
        shade.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        serverLevel.addFreshEntity(shade);
    }

    private static boolean isDense(Level level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() == ModBlocks.DENSE_OBSIDIAN;
    }
}