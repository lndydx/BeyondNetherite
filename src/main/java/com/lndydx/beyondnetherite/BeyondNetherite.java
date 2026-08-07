package com.lndydx.beyondnetherite;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.Identifier;

import com.lndydx.beyondnetherite.entity.ModEntities;
import com.lndydx.beyondnetherite.item.ModArmorMaterials;
import com.lndydx.beyondnetherite.item.ModItems;
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

		ServerTickEvents.END_SERVER_TICK.register(server -> ObsidianSetBonus.restoreMotion());

		LOGGER.info("Beyond Netherite loaded.");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}