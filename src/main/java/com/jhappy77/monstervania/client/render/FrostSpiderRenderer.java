package com.jhappy77.monstervania.client.render;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.client.model.FrankengolemModel;
import com.jhappy77.monstervania.client.model.FrostSpiderModel;
import com.jhappy77.monstervania.client.render.layers.FrostSpiderEyesLayer;
import com.jhappy77.monstervania.entities.FrostSpiderEntity;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.layers.SpiderEyesLayer;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.util.ResourceLocation;

public class FrostSpiderRenderer<T extends FrostSpiderEntity> extends MobRenderer<T, SpiderModel<T>>{
    protected static final ResourceLocation TEXTURE =  new ResourceLocation(Monstervania.MOD_ID, "textures/entity/frost_spider.png");

    public FrostSpiderRenderer(EntityRendererManager rendererManagerIn){
        //super(rendererManagerIn);

        super(rendererManagerIn, new SpiderModel<>(), 0.8f);
        this.addLayer(new FrostSpiderEyesLayer<>(this));
    }

    public ResourceLocation getEntityTexture(T entity) {
        return TEXTURE;
    }
}
//MobRenderer<FrostSpiderEntity, FrostSpiderModel<FrostSpiderEntity>>

