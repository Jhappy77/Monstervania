package com.jhappy77.monstervania.entities;

import com.jhappy77.monstervania.util.MvSpawnCondition;
import com.jhappy77.monstervania.util.MvSpawnable;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.AnimationController;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

import java.util.ArrayList;
import java.util.List;

public class RatEntity extends MonsterEntity implements IAnimatedEntity, MvSpawnable {

    private EntityAnimationManager manager = new EntityAnimationManager();
    private AnimationController controller = new EntityAnimationController(this, "moveController",20, this::animationPredicate);

//    private boolean animationPredicate(AnimationTestEvent event) {
//    }

    public RatEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        registerAnimationControllers();
    }

    // func_233666_p_ = registerAttributes
    public static AttributeModifierMap.MutableAttribute setCustomAttributes(){
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.7F);
    }


    private <E extends RatEntity> boolean animationPredicate(AnimationTestEvent<E> event) {
        if(event.isWalking()){
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.monstervania.simplewalk", true));
            return true;
        }else if (false) {
            // Attack animation
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.monstervania.claw"));
            return true;
        } else{
            //play idle
            return true;
        }
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0f,true));
        this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    public static List<MvSpawnCondition> spawnConditions() {
        ArrayList<MvSpawnCondition> conditions = new ArrayList<>();
        conditions.add(new MvSpawnCondition(50, 1, 4).restrictToOverworld().restrictToLand()
        .addWorldSpawnClause(
                new MvSpawnCondition.PositionAltitudeClause().setMaxY(50)
        ));
        return conditions;
    }

    public List<MvSpawnCondition> getSpawnConditions(){
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

    @Override
    public EntityAnimationManager getAnimationManager() {
        return manager;
    }

    private void registerAnimationControllers(){
        manager.addAnimationController(controller);
    }
}
