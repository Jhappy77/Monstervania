package com.jhappy77.monstervania.entities;

import com.jhappy77.monstervania.util.MvMobSpawnInfo;
import com.jhappy77.monstervania.util.MvSpawnCondition;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MummifiedCreeperEntity extends CreeperEntity {

    public MummifiedCreeperEntity(EntityType<? extends MummifiedCreeperEntity> type, World worldIn) {
        super(type, worldIn);
    }


    public static AttributeModifierMap.MutableAttribute setCustomAttributes(){
        return CreeperEntity.registerAttributes();
    }

    private int explosionRadius = 3;
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;

    public void tick() {
        if (this.isAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;
            if (this.hasIgnited()) {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();
            if (i > 0 && this.timeSinceIgnited == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 2.0F);
            }

            this.timeSinceIgnited += i;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                this.explode();
            }
        }

        super.tick();
    }

    private void explode() {
        if (!this.world.isRemote) {
            Explosion.Mode explosion$mode = Explosion.Mode.NONE;
            float f = this.isCharged() ? 2.0F : 1.0F;
            this.dead = true;
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), (float)this.explosionRadius * f, explosion$mode);
            this.remove();
            this.spawnLingeringCloud();
        }
    }

    private void spawnLingeringCloud() {
            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());
            areaeffectcloudentity.setRadius(4.5F);
            areaeffectcloudentity.setRadiusOnUse(-0.5F);
            areaeffectcloudentity.setWaitTime(10);
            areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
            areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
            areaeffectcloudentity.addEffect(new EffectInstance(Effects.WITHER, 80, 2));
            this.world.addEntity(areaeffectcloudentity);
        }

    public static List<MvSpawnCondition<MvMobSpawnInfo>> spawnConditions() {
        ArrayList<MvSpawnCondition<MvMobSpawnInfo>> conditions = new ArrayList<>();
        conditions.add(new MvSpawnCondition(new MvMobSpawnInfo(100, 1, 1)).addBiomeSpawnClause(
                new MvSpawnCondition.BiomeCategorySpawnClause().addCategory(Biome.Category.DESERT)
        ));
        return conditions;
    }

    }
// Creeper Stats
    //    public static AttributeModifierMap.MutableAttribute setCustomAttributes(){
//        return MobEntity.func_233666_p_()
//                .createMutableAttribute(Attributes.MAX_HEALTH, 16)
//                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35f)
//                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1)
//                .createMutableAttribute(Attributes.ATTACK_SPEED, 1)
//                .createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D);
//    }
//

