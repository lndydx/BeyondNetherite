package com.lndydx.beyondnetherite.client.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

import com.lndydx.beyondnetherite.BeyondNetherite;
import com.lndydx.beyondnetherite.entity.ObsidianArrow;

public class ObsidianArrowRendererer extends ArrowRenderer<ObsidianArrow, ArrowRenderState> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(
            BeyondNetherite.MOD_ID, "textures/entity/obsidian_arrow_entity.png");

    public ObsidianArrowRendererer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    protected Identifier getTextureLocation(ArrowRenderState state) {
        return TEXTURE;
    }
}