package com.jhappy77.monstervania.events;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.entities.FrankengolemEntity;
import com.jhappy77.monstervania.init.ModEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.Difficulty;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;

public class LightningEventReaction {

    public static void process (EntityStruckByLightningEvent event){
        Entity e = event.getEntity();
        if(e.isAlive() && e instanceof IronGolemEntity){
            convertIronGolem(event, (IronGolemEntity)e);
        }
    }


    // Converts Iron Golem into Frankengolem
    public static void convertIronGolem(EntityStruckByLightningEvent event, IronGolemEntity ig) {
        ServerWorld s = ig.getServer().func_241755_D_();

        if (s.getDifficulty() != Difficulty.PEACEFUL) {
            FrankengolemEntity frEntity = ModEntityTypes.FRANKENGOLEM.get().create(s);
            frEntity.setLocationAndAngles(ig.getPosX(), ig.getPosY(), ig.getPosZ(), ig.rotationYaw, ig.rotationPitch);
            frEntity.onInitialSpawn(s, s.getDifficultyForLocation(frEntity.getPosition()), SpawnReason.CONVERSION, (ILivingEntityData) null, (CompoundNBT) null);
            frEntity.setNoAI(ig.isAIDisabled());
            if (ig.hasCustomName()) {
                frEntity.setCustomName(ig.getCustomName());
                frEntity.setCustomNameVisible(ig.isCustomNameVisible());
            }

            frEntity.enablePersistence();
            s.func_242417_l(frEntity);
            ig.remove();
        }
    }

}
