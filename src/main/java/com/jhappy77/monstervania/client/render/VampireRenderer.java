package com.jhappy77.monstervania.client.render;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.client.model.VampireModel;
import com.jhappy77.monstervania.entities.VampireEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class VampireRenderer extends MobRenderer<VampireEntity, VampireModel<VampireEntity>> {

    protected static final ResourceLocation TEXTURE =  new ResourceLocation(Monstervania.MOD_ID, "textures/entity/vampire.png");

    public VampireRenderer(EntityRendererManager rendererManagerIn){
        super(rendererManagerIn, new VampireModel<>(), 0.8f);
    }

    @Override
    public ResourceLocation getEntityTexture(VampireEntity entity) {
        return TEXTURE;
    }
}
