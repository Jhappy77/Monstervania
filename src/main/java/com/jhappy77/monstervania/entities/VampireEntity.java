package com.jhappy77.monstervania.entities;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.goals.FleeSunEarlyGoal;
import com.jhappy77.monstervania.init.SoundInit;
import com.jhappy77.monstervania.util.*;
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
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


import java.util.ArrayList;
import java.util.List;

public class VampireEntity extends MonsterEntity implements MvDamageModifiable, MvEntitySpawnable {

    private int attackTimer = 0;

    public VampireEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }


    public static List<MvSpawnCondition<MvMobSpawnInfo>> spawnConditions() {
        ArrayList<MvSpawnCondition<MvMobSpawnInfo>> conditions = new ArrayList<>();
        conditions.add(new MvSpawnCondition(new MvMobSpawnInfo(80, 1, 1))
                .restrictToOverworld().restrictToLand().monsterSpawnTime());
        return conditions;
    }

    @Override
    public void livingTick(){
        if(this.isInDaylight()){
            this.attackEntityFrom(DamageSource.DRYOUT, 3.5f);
            BlockState b = this.world.getBlockState(this.getOnPosition());
            for(int i = 0; i < 2; i++) {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                if(world instanceof ServerWorld){
                    ((ServerWorld)world).spawnParticle(ParticleTypes.POOF, this.getPosX(), this.getPosY()+1, this.getPosZ(), 10, 0.0D, 0.0D, 0.0D, (double)0.15F);
                }
                //this.getEntityWorld().addParticle(ParticleTypes.POOF, this.getPosXRandom(1.0D), this.getPosYRandom(), this.getPosZRandom(1.0D), d0, d1, d2);
            }
        }
        if(this.attackTimer > 0){
            this.attackTimer--;
        }
        super.livingTick();
    }


    public List<MvSpawnCondition<MvMobSpawnInfo>> getSpawnConditions(){
        return spawnConditions();
    }

    // func_233666_p_ = registerAttributes
    public static AttributeModifierMap.MutableAttribute setCustomAttributes(){
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1)
                .createMutableAttribute(Attributes.ATTACK_SPEED, 1.5)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, -0.5)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D);
    }


    // The lower the priority number, the more likely mob is to do task
    @Override
    protected void registerGoals(){
        //super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0f,true));
        this.goalSelector.addGoal(0, new RestrictSunGoal(this));
        this.goalSelector.addGoal(1, new FleeSunEarlyGoal(this, 0.5F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, false));
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn){
        //If the vampire attacks a player or a villager, it heals itself by a slight amount
        this.attackTimer = 10;
        if(entityIn instanceof PlayerEntity){
            this.heal(1);
        } else if (entityIn instanceof VillagerEntity){
            this.heal(1);
        }
        return super.attackEntityAsMob(entityIn);
    }




    public int getAttackTimer(){return attackTimer;}


    protected SoundEvent getAmbientSound() {
        return SoundInit.VAMPIRE_AMBIENT.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundInit.ENTITY_VAMPIRE_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return SoundInit.ENTITY_VAMPIRE_DEATH.get();
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }


    @Override
    public MvDamageModifier[] getMvDamageModifiers() {
        MvDamageModifier[] modifiers = new MvDamageModifier[3];
        modifiers[0] = new MvDamageModifier(MvDamageModifierType.WOOD, 1.5F);
        modifiers[1] = new MvDamageModifier(MvDamageModifierType.FALL, 0.6F);
        modifiers[2] = new MvDamageModifier(MvDamageModifierType.FIRE, 1.25F);
        return modifiers;
    }
}

