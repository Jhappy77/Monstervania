package com.jhappy77.monstervania.entities;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.util.MvMobSpawnInfo;
import com.jhappy77.monstervania.util.MvSpawnCondition;
import net.minecraft.client.renderer.entity.HuskRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class FrostSpiderEntity extends SpiderEntity {

    public FrostSpiderEntity(EntityType<? extends FrostSpiderEntity> type, World worldIn) {
        super(type, worldIn);
    }

    // func_233666_p_ = registerAttributes
    public static AttributeModifierMap.MutableAttribute setCustomAttributes(){
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 16)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35f)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1)
                .createMutableAttribute(Attributes.ATTACK_SPEED, 1)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D);
    }

    //HuskRenderer

    @Override
    public boolean attackEntityAsMob(Entity entityIn){
        Monstervania.LOGGER.debug("Frost spider attacked!");
        //EntityIn is the one being attacked
        if(entityIn.isAlive() && entityIn instanceof LivingEntity){
            LivingEntity e = (LivingEntity) entityIn;
            EffectInstance myEffect = new EffectInstance(Effects.SLOWNESS, 500, 0);
            if(e.isPotionApplicable(myEffect)){
                e.addPotionEffect(myEffect);
            }
        }
        return super.attackEntityAsMob(entityIn);
    }

    public static List<MvSpawnCondition<MvMobSpawnInfo>> spawnConditions() {
        ArrayList<MvSpawnCondition<MvMobSpawnInfo>> conditions = new ArrayList<>();
        conditions.add(new MvSpawnCondition(new MvMobSpawnInfo(100, 1, 1))
                .restrictToOverworld().restrictToLand().monsterSpawnTime()
                .addBiomeSpawnClause(
                new MvSpawnCondition.BiomeTemperatureClause().setMaxTemp(0.05f)
        ));
        return conditions;
    }

}
