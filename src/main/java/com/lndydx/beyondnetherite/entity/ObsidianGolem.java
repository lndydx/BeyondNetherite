package com.lndydx.beyondnetherite.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundEvents;

import com.lndydx.beyondnetherite.item.ModItems;

public class ObsidianGolem extends IronGolem {
    public ObsidianGolem(EntityType<? extends IronGolem> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return IronGolem.createAttributes()
                .add(Attributes.MAX_HEALTH, 250.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 25.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float multiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void dropAllDeathLoot(ServerLevel level, DamageSource damageSource) {
        super.dropAllDeathLoot(level, damageSource);

        int shards = 3 + level.getRandom().nextInt(3);
        this.spawnAtLocation(level, new ItemStack(ModItems.OBSIDIAN_SHARD, shards));

        int obsidian = level.getRandom().nextInt(3);
        if (obsidian > 0) {
            this.spawnAtLocation(level, new ItemStack(Items.OBSIDIAN, obsidian));
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.OBSIDIAN) && this.getHealth() < this.getMaxHealth()) {
            this.heal(50.0F);
            stack.consume(1, player);
            this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }
}