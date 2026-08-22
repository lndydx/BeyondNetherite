package com.lndydx.beyondnetherite.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.resources.Identifier;

import com.lndydx.beyondnetherite.BeyondNetherite;
import com.lndydx.beyondnetherite.client.renderer.ObsidianArrowRendererer;
import com.lndydx.beyondnetherite.client.renderer.ShadeRenderer;
import com.lndydx.beyondnetherite.entity.ModEntities;

public class BeyondNetheriteClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.OBSIDIAN_ARROW, ObsidianArrowRendererer::new);
		EntityRendererRegistry.register(ModEntities.OBSIDIAN_GOLEM, ObsidianGolemRenderer::new);
		EntityRendererRegistry.register(ModEntities.SHADE, ShadeRenderer::new);
	}

	private static class ObsidianGolemRenderer extends IronGolemRenderer {
		private static final Identifier OBSIDIAN_GOLEM_TEXTURE =
				Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, "textures/entity/obsidian_golem.png");

		public ObsidianGolemRenderer(EntityRendererProvider.Context context) {
			super(context);
		}

		@Override
		public Identifier getTextureLocation(net.minecraft.client.renderer.entity.state.IronGolemRenderState state) {
			return OBSIDIAN_GOLEM_TEXTURE;
		}
	}
}