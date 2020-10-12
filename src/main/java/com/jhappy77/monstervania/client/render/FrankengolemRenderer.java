package com.jhappy77.monstervania.client.render;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.client.model.FrankengolemModel;
import com.jhappy77.monstervania.entities.FrankengolemEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.HuskRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class FrankengolemRenderer extends MobRenderer<FrankengolemEntity, FrankengolemModel<FrankengolemEntity>> {
    protected static final ResourceLocation TEXTURE =  new ResourceLocation(Monstervania.MOD_ID, "textures/entity/frankengolem.png");

    public FrankengolemRenderer(EntityRendererManager rendererManagerIn){
        super(rendererManagerIn, new FrankengolemModel<>(), 0.8f);
    }

    @Override
    public ResourceLocation getEntityTexture(FrankengolemEntity entity) {
        return TEXTURE;
    }
}
