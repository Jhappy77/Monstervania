package com.jhappy77.monstervania.entities;

import com.jhappy77.monstervania.goals.RatAttackGoal;
import com.jhappy77.monstervania.util.MvEntitySpawnable;
import com.jhappy77.monstervania.util.MvMobSpawnInfo;
import com.jhappy77.monstervania.util.MvSpawnCondition;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

//Geckolib
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.List;

public class RatEntity extends MonsterEntity implements IAnimatable, MvEntitySpawnable {

    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(RatEntity.class, DataSerializers.BOOLEAN);

    private AnimationFactory factory = new AnimationFactory(this);

    public RatEntity(EntityType<? extends RatEntity> type, World worldIn)
    {
        super(type, worldIn);
        this.ignoreFrustumCheck = true;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if(event.isMoving() && !this.dataManager.get(ATTACKING)){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rat.walk", true));
            return PlayState.CONTINUE;
        }
        if(this.dataManager.get(ATTACKING) && !deadOrDying()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rat.claw", false));
            return PlayState.CONTINUE;
        }
        if(deadOrDying()){
            // Death animation
//            if (world.isRemote) {
//                event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
//                return PlayState.CONTINUE;
//            }
        }
        // Idle
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rat.idle", true));
        return PlayState.CONTINUE;
    }

    private boolean deadOrDying(){
        return (this.dead || this.getHealth() < 0.01);
    }

    public void setAttacking(boolean attacking) {
        this.dataManager.set(ATTACKING, attacking);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ATTACKING, false);
    }


    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }

    // func_233666_p_ = registerAttributes
    public static AttributeModifierMap.MutableAttribute setCustomAttributes(){
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 1.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new RatAttackGoal(this, 1.0f,true));
        this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    public static List<MvSpawnCondition<MvMobSpawnInfo>> spawnConditions() {
        ArrayList<MvSpawnCondition<MvMobSpawnInfo>> conditions = new ArrayList<>();
        conditions.add(new MvSpawnCondition(new MvMobSpawnInfo(50, 1, 4))
                .restrictToOverworld().restrictToLand().monsterSpawnTime()
        .addWorldSpawnClause(
                new MvSpawnCondition.PositionAltitudeClause().setMaxY(50)
        ));
        return conditions;
    }

    public List<MvSpawnCondition<MvMobSpawnInfo>> getSpawnConditions(){
        return spawnConditions();
    }


    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

}
