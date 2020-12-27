package com.jhappy77.monstervania.entities;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.goals.FleeSunEarlyGoal;
import com.jhappy77.monstervania.lists.ParticleList;
import com.jhappy77.monstervania.util.*;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
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
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class VampireEntity extends MonsterEntity implements MvDamageModifiable, MvEntitySpawnable {

    private int attackTimer = 0;
    private int deathTicks = 0;
    private int ticksLightCountdown = 0;


    private ClientWorld clientWorld;
    private boolean isClient;

    public VampireEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        if(worldIn instanceof ClientWorld){
            this.clientWorld = (ClientWorld)worldIn;
            isClient = true;
        }
    }


    public static List<MvSpawnCondition<MvMobSpawnInfo>> spawnConditions() {
        ArrayList<MvSpawnCondition<MvMobSpawnInfo>> conditions = new ArrayList<>();
        conditions.add(new MvSpawnCondition(new MvMobSpawnInfo(80, 1, 1))
                .restrictToOverworld().restrictToLand().monsterSpawnTime());
        return conditions;
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
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D);
    }


    // The lower the priority number, the more likely mob is to do task
    @Override
    protected void registerGoals(){
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0f,true));
        this.goalSelector.addGoal(0, new RestrictSunGoal(this));
        this.goalSelector.addGoal(1, new FleeSunEarlyGoal(this, 1.0f));
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
//        if (this.attackTimer > 0) {
//            --this.attackTimer;
//        }
        if(this.isInDaylight()){
            spawnDissolveParticles();
        }
        super.livingTick();
    }

    @OnlyIn(Dist.CLIENT)
    private void spawnDissolveParticles(){
        //Monstervania.LOGGER.debug("Vampire world: " + this.world.toString());
        //this.world.addParticle(getEnergyParticle(), true, 0, 100, 0, 1,1,1);
//        int j = this.world.rand.nextInt(10) + 20;
//        for(int i=0; i<j; i++) {
//            float f1 = (float) ((this.world.rand.nextInt(10)) / 4 * Math.pow(-1, this.world.rand.nextInt(2)));
//            float f2 = (float) ((this.world.rand.nextInt(10)) / 4 * Math.pow(-1, this.world.rand.nextInt(2)));
//            float f3 = (float) ((this.world.rand.nextInt(10)) / 4 * Math.pow(-1, this.world.rand.nextInt(2)));
//            this.world.addParticle(this.getEnergyParticle(), this.getPosX() + f1, this.getPosY() + 1.0F + f2, this.getPosZ() + f3, 0.0D, 0.0D, 0.0D);
//            Monstervania.LOGGER.debug("Should be spawning vamp particles");
//        }
        if(this.isClient){
            for(int i=0; i<10; i++) {
                float f1 = (float) ((this.world.rand.nextInt(10)) / 4 * Math.pow(-1, this.world.rand.nextInt(2)));
                float f2 = (float) ((this.world.rand.nextInt(10)) / 4 * Math.pow(-1, this.world.rand.nextInt(2)));
                float f3 = (float) ((this.world.rand.nextInt(10)) / 4 * Math.pow(-1, this.world.rand.nextInt(2)));
                if(this.clientWorld != null)
                    this.clientWorld.addParticle(this.getEnergyParticle(), this.getPosX() + f1, this.getPosY() + 1.0F + f2, this.getPosZ() + f3, 0.0D, 0.0D, 0.0D);
                else
                    Monstervania.LOGGER.debug("Somehow clientworld is null");
            }
            Monstervania.LOGGER.debug("Should be spawning vamp particles");
        }
        this.attackEntityFrom(DamageSource.DRYOUT, 3.0f);
    }

//    public void checkIfInDaylight(){
//        if(this.isInDaylight()){
//
//            if(ticksLightCountdown == 30){
//                this.setHealth(0);
//            } else{
//                Monstervania.LOGGER.debug("Should be spawning vamp particles");
//                int j = this.world.rand.nextInt(10) + 20;
//                for(int i=0; i<j; i++) {
//                    float f1 = (float) ((this.world.rand.nextInt(10)) / 4 * Math.pow(-1, this.world.rand.nextInt(2)));
//                    float f2 = (float) ((this.world.rand.nextInt(10)) / 4 * Math.pow(-1, this.world.rand.nextInt(2)));
//                    float f3 = (float) ((this.world.rand.nextInt(10)) / 4 * Math.pow(-1, this.world.rand.nextInt(2)));
//                    this.world.addParticle(this.getEnergyParticle(), this.getPosX() + f1, this.getPosY() + 1.0F + f2, this.getPosZ() + f3, 0.0D, 0.0D, 0.0D);
//                }
//                ticksLightCountdown++;
//            }
//
//            //ParticleType.EXPLOSION
//
//        } else{
//            ticksLightCountdown = 0;
//        }
//    }

    protected IParticleData getEnergyParticle() {
        return ParticleList.ELECTRIC_PARTICLE.get();
    }


    public int getAttackTimer(){return attackTimer;}

//    protected void onDeathUpdate(){
//        ++this.deathTicks;
//        if (this.deathTicks >= 180 && this.deathTicks <= 200) {
//            float f = (this.rand.nextFloat() - 0.5F) * 2.0F;
//            float f1 = (this.rand.nextFloat() - 0.5F) * 1.0F;
//            float f2 = (this.rand.nextFloat() - 0.5F) * 2.0F;
//            this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getPosX() + (double)f, this.getPosY() + 2.0D + (double)f1, this.getPosZ() + (double)f2, 0.0D, 0.0D, 0.0D);
//        }
//        super.onDeathUpdate();
//    }

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

