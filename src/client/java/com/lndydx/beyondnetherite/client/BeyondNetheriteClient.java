package com.lndydx.beyondnetherite.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;

import com.lndydx.beyondnetherite.client.renderer.ObsidianArrowRenderer;
import com.lndydx.beyondnetherite.entity.ModEntities;

public class BeyondNetheriteClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRenderers.register(ModEntities.OBSIDIAN_ARROW, ObsidianArrowRenderer::new);
	}
}