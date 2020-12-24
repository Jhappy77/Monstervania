package com.jhappy77.monstervania.entities;

import com.jhappy77.monstervania.lists.ParticleList;
import com.jhappy77.monstervania.util.*;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.model.IronGolemModel;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;


public class FrankengolemEntity extends MonsterEntity implements MvDamageModifiable {
    private int attackTimer = 0;
    private int particleTimer = 0;
    private int attackCooldown = 0;

    public FrankengolemEntity(EntityType<? extends MonsterEntity> type, World worldIn) {

        super(type, worldIn);
        this.stepHeight = 1.0F;
    }

    //IronGolemEntity
    //IronGolemModel
    @Override
    protected void registerGoals(){
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.05f,true));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 75.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .createMutableAttribute(Attributes.ATTACK_SPEED, 0.00000000001D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 15.0D);

    }

    /**
     * Entity updates state
     */
    public void livingTick() {
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        if (this.attackCooldown > 0) {
            --this.attackCooldown;
        }
        if(this.particleTimer == 40){
            spawnEnergyParticles();
            particleTimer = 0;
        }
        particleTimer++;
        super.livingTick();
    }

    private void spawnEnergyParticles(){
        int j = this.world.rand.nextInt(10) + 20;
        for(int i=0; i<j; i++) {
            float f1 = (float) ((this.world.rand.nextInt(10)) / 4 * Math.pow(-1, this.world.rand.nextInt(2)));
            float f2 = (float) ((this.world.rand.nextInt(10)) / 4 * Math.pow(-1, this.world.rand.nextInt(2)));
            float f3 = (float) ((this.world.rand.nextInt(10)) / 4 * Math.pow(-1, this.world.rand.nextInt(2)));
            this.world.addParticle(this.getEnergyParticle(), this.getPosX() + f1, this.getPosY() + 1.0F + f2, this.getPosZ() + f3, 0.0D, 0.0D, 0.0D);
        }
        }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @OnlyIn(Dist.CLIENT)
    public int getAttackTimer(){
        return attackTimer;
    }

    // Attack Method

    public boolean attackEntityAsMob(Entity entityIn) {
        if(attackCooldown > 0)
            return true;
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte)4);
        float f = this.getAttackDamage();
        float f1 = (int)f > 0 ? f / 2.0F + (float)this.rand.nextInt((int)f) : f;
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f1);
        if (flag) {
            entityIn.setMotion(entityIn.getMotion().add(0.0D, 0.4F, 0.0D));
            this.applyEnchantments(this, entityIn);
        }
        attackCooldown = 20;
        playAttackSound();
        spawnEnergyParticles();
        return flag;
    }

    public MvDamageModifier[] getMvDamageModifiers() {
        MvDamageModifier[] modifiers = new MvDamageModifier[1];
        modifiers[0] = new MvDamageModifier(MvDamageModifierType.ELECTRIC, -0.3F);
        return modifiers;
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0D, (double)(0.875F * this.getEyeHeight()), (double)(this.getWidth() * 0.4F));
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            this.attackTimer = 10;
            playAttackSound();
        }
        else {
            super.handleStatusUpdate(id);
        }

    }
    //TODO: Update everything below


    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_IRON_GOLEM_HURT;
    }

    protected void playHurtSound(DamageSource sourceIn){
        this.playSound(this.getHurtSound(sourceIn), 0.15F, 0.05F);
    }

    protected void playAttackSound(){ this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 0.4F);}

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRON_GOLEM_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_IRON_GOLEM_STEP;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    protected IParticleData getEnergyParticle() {
        return ParticleList.ELECTRIC_PARTICLE.get();
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEFINED;
    }

    public static List<MvSpawnCondition<MvMobSpawnInfo>> spawnConditions() {
        ArrayList<MvSpawnCondition<MvMobSpawnInfo>> conditions = new ArrayList<>();
        conditions.add(new MvSpawnCondition<MvMobSpawnInfo>(new MvMobSpawnInfo(5, 1, 1)).restrictToLand().restrictToOverworld());
        return conditions;
    }
}


