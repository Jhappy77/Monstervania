package com.jhappy77.monstervania.client.render;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.client.model.RatModel;
import com.jhappy77.monstervania.client.render.layers.RatEyesLayer;
import com.jhappy77.monstervania.entities.RatEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RatRenderer extends MobRenderer<RatEntity, RatModel<RatEntity>> {
    protected static final ResourceLocation TEXTURE =  new ResourceLocation(Monstervania.MOD_ID, "textures/entity/rat.png");

    public RatRenderer(EntityRendererManager rendererManagerIn){

        super(rendererManagerIn, new RatModel<>(), 0.8f);
        this.addLayer(new RatEyesLayer<>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(RatEntity entity) {
        return TEXTURE;
    }
}
