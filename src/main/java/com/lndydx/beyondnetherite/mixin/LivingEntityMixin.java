package com.lndydx.beyondnetherite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import com.lndydx.beyondnetherite.item.ObsidianSetBonus;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique
    private static final ThreadLocal<Boolean> BEYOND_NETHERITE_APPLYING = ThreadLocal.withInitial(() -> Boolean.FALSE);

    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void beyondnetherite$reduceExplosionDamage(ServerLevel level, DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (BEYOND_NETHERITE_APPLYING.get()) {
            return;
        }
        LivingEntity self = (LivingEntity) (Object) this;
        if (damageSource.is(DamageTypeTags.IS_EXPLOSION) && ObsidianSetBonus.hasFullSet(self)) {
            ObsidianSetBonus.saveMotion(self);
            BEYOND_NETHERITE_APPLYING.set(Boolean.TRUE);
            try {
                cir.setReturnValue(self.hurtServer(level, damageSource, amount * 0.7F));
            } finally {
                BEYOND_NETHERITE_APPLYING.set(Boolean.FALSE);
            }
        }
    }
}