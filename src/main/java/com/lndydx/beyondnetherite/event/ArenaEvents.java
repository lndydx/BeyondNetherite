package com.lndydx.beyondnetherite.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import com.lndydx.beyondnetherite.arena.ShadeArena;
import com.lndydx.beyondnetherite.arena.ShadeArenaManager;

public class ArenaEvents {
    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) ->
                !ShadeArenaManager.shouldRestrictPlayer(player));

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (ShadeArenaManager.shouldRestrictPlayer(player)) {
                ItemStack stack = player.getItemInHand(hand);
                if (stack.getItem() instanceof BlockItem || stack.getItem() instanceof BucketItem) {
                    return InteractionResult.FAIL;
                }
            }
            return InteractionResult.PASS;
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (ShadeArenaManager.shouldRestrictPlayer(player)
                    && player.getItemInHand(hand).is(Items.ENDER_PEARL)) {
                return InteractionResult.FAIL;
            }
            return InteractionResult.PASS;
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (newPlayer.isCreative() || newPlayer.isSpectator()) return;
            ServerLevel level = (ServerLevel) newPlayer.level();
            ShadeArena arena = ShadeArenaManager.getArena(level);
            if (arena != null && arena.isShadeAlive()) {
                newPlayer.teleportTo(arena.getCenterX(), arena.getCenter().getY() + 1.0, arena.getCenterZ());
            }
        });
    }
}