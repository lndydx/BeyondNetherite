package com.lndydx.beyondnetherite;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;

import com.lndydx.beyondnetherite.arena.ShadeArenaManager;
import com.lndydx.beyondnetherite.block.ModBlocks;
import com.lndydx.beyondnetherite.entity.ModEntities;
import com.lndydx.beyondnetherite.event.ArenaEvents;
import com.lndydx.beyondnetherite.item.ModArmorMaterials;
import com.lndydx.beyondnetherite.item.ModItems;
import com.lndydx.beyondnetherite.item.ModRecipes;
import com.lndydx.beyondnetherite.item.ObsidianSetBonus;
import com.lndydx.beyondnetherite.loot.ModLoot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeyondNetherite implements ModInitializer {
	public static final String MOD_ID = "beyond-netherite";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModArmorMaterials.initialize();
		ModItems.initialize();
		ModEntities.initialize();
		ModLoot.initialize();
		ModRecipes.initialize();
		ModBlocks.initialize();
		ArenaEvents.register();

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			ObsidianSetBonus.restoreMotion();
			for (ServerLevel level : server.getAllLevels()) {
				ShadeArenaManager.tick(level);
			}
		});

		LOGGER.info("Beyond Netherite loaded.");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}