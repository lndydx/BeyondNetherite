package com.lndydx.beyondnetherite.client.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.client.renderer.entity.state.TippableArrowRenderState;
import net.minecraft.resources.Identifier;

import com.lndydx.beyondnetherite.entity.ObsidianArrow;

public class ObsidianArrowRenderer extends ArrowRenderer<ObsidianArrow, TippableArrowRenderState> {
    public ObsidianArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public TippableArrowRenderState createRenderState() {
        return new TippableArrowRenderState();
    }

    @Override
    public void extractRenderState(ObsidianArrow entity, TippableArrowRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
    }

    @Override
    protected Identifier getTextureLocation(TippableArrowRenderState state) {
        return TippableArrowRenderer.NORMAL_ARROW_LOCATION;
    }
}