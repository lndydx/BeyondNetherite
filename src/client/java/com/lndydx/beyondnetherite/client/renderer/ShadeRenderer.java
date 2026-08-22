package com.lndydx.beyondnetherite.client.renderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

import com.lndydx.beyondnetherite.BeyondNetherite;
import com.lndydx.beyondnetherite.entity.Shade;

public class ShadeRenderer extends HumanoidMobRenderer<Shade, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {
    private static final Identifier SHADE_TEXTURE =
            Identifier.fromNamespaceAndPath(BeyondNetherite.MOD_ID, "textures/entity/shade.png");

    public ShadeRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE)), 0.5F);
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public Identifier getTextureLocation(HumanoidRenderState state) {
        return SHADE_TEXTURE;
    }
}