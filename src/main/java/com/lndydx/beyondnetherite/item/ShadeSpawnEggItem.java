package com.lndydx.beyondnetherite.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

import com.lndydx.beyondnetherite.entity.ModEntities;
import com.lndydx.beyondnetherite.entity.Shade;

public class ShadeSpawnEggItem extends Item {
    public ShadeSpawnEggItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel() instanceof ServerLevel serverLevel) {
            BlockPos clickedPos = context.getClickedPos().relative(context.getClickedFace());
            Shade shade = new Shade(ModEntities.SHADE, serverLevel);
            shade.setPos(clickedPos.getX() + 0.5, clickedPos.getY(), clickedPos.getZ() + 0.5);
            shade.setYRot(context.getPlayer().getYRot());
            shade.yBodyRot = shade.getYRot();
            shade.yHeadRot = shade.getYRot();
            serverLevel.addFreshEntity(shade);
            if (!context.getPlayer().getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }
}