package com.jhappy77.monstervania.entities;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.util.MvDamageModifiable;
import com.jhappy77.monstervania.util.MvDamageModifier;
import com.jhappy77.monstervania.util.MvDamageModifierType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VampireEntity extends MonsterEntity implements MvDamageModifiable {

    private int attackTimer = 0;

    public VampireEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    // func_233666_p_ = registerAttributes
    public static AttributeModifierMap.MutableAttribute setCustomAttributes(){
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1)
                .createMutableAttribute(Attributes.ATTACK_SPEED, 1.5)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D);
    }

    // The lower the priority number, the more likely mob is to do task
    @Override
    protected void registerGoals(){
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0f,true));
        //this.goalSelector.addGoal(7, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, true));
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn){
        //Monstervania.LOGGER.debug("Vampire attacked entity as mob");
        //EntityIn is the one being attacked
        this.attackTimer = 10;
        if(entityIn instanceof PlayerEntity){
            this.heal(2);
        } else if (entityIn instanceof VillagerEntity){
            this.heal(2);
        }
        return super.attackEntityAsMob(entityIn);
    }

    public void livingTick() {
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        super.livingTick();
    }

    public int getAttackTimer(){return attackTimer;}

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ZOMBIE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIE_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }


    @Override
    public MvDamageModifier[] getMvDamageModifiers() {
        MvDamageModifier[] modifiers = new MvDamageModifier[2];
        modifiers[0] = new MvDamageModifier(MvDamageModifierType.WOOD, 1.5F);
        modifiers[1] = new MvDamageModifier(MvDamageModifierType.FALL, 0.6F);
        return modifiers;
    }
}

