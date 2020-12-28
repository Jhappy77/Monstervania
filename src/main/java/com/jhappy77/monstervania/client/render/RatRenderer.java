package com.jhappy77.monstervania.client.render;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.client.model.RatModel;
//import com.jhappy77.monstervania.client.render.layers.RatEyesLayer;
import com.jhappy77.monstervania.entities.RatEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RatRenderer extends GeoEntityRenderer<RatEntity> {
    protected static final ResourceLocation TEXTURE =  new ResourceLocation(Monstervania.MOD_ID, "textures/entity/rat.png");

    public RatRenderer(EntityRendererManager rendererManagerIn){

        super(rendererManagerIn, new RatModel());
        //this.addLayer(new RatEyesLayer<RatEntity, RatModel<RatEntity>>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(RatEntity entity) {
        return TEXTURE;
    }
}
