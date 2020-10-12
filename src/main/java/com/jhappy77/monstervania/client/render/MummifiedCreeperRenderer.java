package com.jhappy77.monstervania.client.render;

import com.jhappy77.monstervania.Monstervania;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.util.ResourceLocation;

public class MummifiedCreeperRenderer extends CreeperRenderer {
    protected static final ResourceLocation TEXTURE =  new ResourceLocation(Monstervania.MOD_ID, "textures/entity/mummified_creeper.png");

    public MummifiedCreeperRenderer(EntityRendererManager rendererManagerIn){
        super(rendererManagerIn);
        //super(rendererManagerIn, new FrostSpiderModel<>(), 0.8f);
    }

    public ResourceLocation getEntityTexture(CreeperEntity entity) {
        return TEXTURE;
    }
}
