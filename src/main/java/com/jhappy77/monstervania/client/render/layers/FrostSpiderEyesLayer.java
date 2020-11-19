package com.jhappy77.monstervania.client.render.layers;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.client.model.FrostSpiderModel;
import com.jhappy77.monstervania.entities.FrostSpiderEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.layers.SpiderEyesLayer;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
//public class FrostSpiderEyesLayer<T extends Entity, M extends SpiderModel<T>>  extends SpiderEyesLayer<T, M>

public class FrostSpiderEyesLayer<T extends FrostSpiderEntity, M extends SpiderModel<T>>  extends AbstractEyesLayer<T, M> {

    protected static final ResourceLocation TEXTURE =  new ResourceLocation(Monstervania.MOD_ID, "textures/entity/layers/frost_spider_eyes.png");

    private static final RenderType RENDER_TYPE = RenderType.getEyes(TEXTURE);

    public FrostSpiderEyesLayer(IEntityRenderer<T, M> rendererIn) {

        super(rendererIn);
    }

    public RenderType getRenderType() {
        return RENDER_TYPE;
    }
}
