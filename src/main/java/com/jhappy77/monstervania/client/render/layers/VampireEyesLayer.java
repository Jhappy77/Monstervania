package com.jhappy77.monstervania.client.render.layers;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.client.model.VampireModel;
import com.jhappy77.monstervania.entities.VampireEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class VampireEyesLayer <T extends VampireEntity, M extends VampireModel<T>> extends AbstractEyesLayer<T, M> {
    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation(Monstervania.MOD_ID, "textures/entity/layers/vampire_eyes.png"));

    public VampireEyesLayer(IEntityRenderer<T, M> rendererIn) {
        super(rendererIn);
    }

    public RenderType getRenderType() {
        return RENDER_TYPE;
    }
}