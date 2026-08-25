package com.lndydx.beyondnetherite;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import com.lndydx.beyondnetherite.arena.ShadeArenaManager;
import com.lndydx.beyondnetherite.block.ModBlocks;
import com.lndydx.beyondnetherite.entity.ModEntities;
import com.lndydx.beyondnetherite.event.ArenaEvents;
import com.lndydx.beyondnetherite.item.ModArmorMaterials;
import com.lndydx.beyondnetherite.item.ModItems;
import com.lndydx.beyondnetherite.item.ModRecipes;
import com.lndydx.beyondnetherite.item.ObsidianSetBonus;
import com.lndydx.beyondnetherite.loot.ModLoot;
import com.lndydx.beyondnetherite.worldgen.ModFeatures;
import com.lndydx.beyondnetherite.worldgen.ModCommands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeyondNetherite implements ModInitializer {
	public static final String MOD_ID = "beyond-netherite";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final Set<UUID> wingedSoundPlayed = new HashSet<>();

	@Override
	public void onInitialize() {
		ModArmorMaterials.initialize();
		ModItems.initialize();
		ModEntities.initialize();
		ModLoot.initialize();
		ModRecipes.initialize();
		ModBlocks.initialize();
		ArenaEvents.register();
		ModFeatures.initialize();
		ModCommands.initialize();

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			ObsidianSetBonus.restoreMotion();
			for (ServerLevel level : server.getAllLevels()) {
				ShadeArenaManager.tick(level);
				for (Player player : level.players()) {
					ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
					boolean winged = chest.is(ModItems.WINGED_OBSIDIAN_CHESTPLATE)
							|| chest.is(ModItems.WINGED_NETHERITE_CHESTPLATE);
					if (winged && wingedSoundPlayed.add(player.getUUID())) {
						player.playSound(SoundEvents.PLAYER_LEVELUP, 1.0F, 1.0F);
					} else if (!winged) {
						wingedSoundPlayed.remove(player.getUUID());
					}
				}
			}
		});

		LOGGER.info("Beyond Netherite loaded.");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}