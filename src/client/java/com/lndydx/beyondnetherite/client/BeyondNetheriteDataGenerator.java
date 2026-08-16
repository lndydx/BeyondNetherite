package com.lndydx.beyondnetherite.client;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import com.lndydx.beyondnetherite.client.datagen.BeyondNetheriteBlockTagProvider;
import com.lndydx.beyondnetherite.client.datagen.BeyondNetheriteRecipeProvider;

public class BeyondNetheriteDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(BeyondNetheriteRecipeProvider::new);
		pack.addProvider(BeyondNetheriteBlockTagProvider::new);
	}
}