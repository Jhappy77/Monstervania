package com.jhappy77.monstervania.events;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.util.MvDamageModifiable;
import com.jhappy77.monstervania.util.MvDamageModifier;
import com.jhappy77.monstervania.util.MvDamageModifierType;
import net.minecraft.client.particle.DragonBreathParticle;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class LivingDamageEventReaction {


    public static void process(LivingDamageEvent e){

        // Checks to see if MvDamageModifer system applies to this entity. If not, returns.
        LivingEntity thisEntity = e.getEntityLiving();
        if(!(thisEntity instanceof MvDamageModifiable)){
            return;
        }

        // Gets the damage modifiers for thisEntity.
        MvDamageModifier[] damageModifiers = ((MvDamageModifiable)thisEntity).getMvDamageModifiers();

        DamageSource source = e.getSource();
        //Initial Damage before multipliers
        float amount = e.getAmount();


        Item enemyItem = null;
        Entity entitySource;

        // Gets enemy's item, if applicable
        //Monstervania.LOGGER.debug("Damage type is: " + source.getDamageType());
        if(source instanceof EntityDamageSource){
            entitySource = ((EntityDamageSource)source).getTrueSource();
            //Monstervania.LOGGER.debug("true source is" + entitySource.toString());
            if(entitySource instanceof LivingEntity){
                LivingEntity liveEntitySource = (LivingEntity)entitySource;
                ItemStack heldStack = (liveEntitySource != null ? (liveEntitySource.getHeldItemMainhand()): ItemStack.EMPTY);
                enemyItem = heldStack.getItem();
                //Monstervania.LOGGER.debug("Enemy item is: " + enemyItem.toString());
            }

        }
//        ItemStack heldStack = (trueSource != null ? (thisEntity.getHeldItemMainhand()): ItemStack.EMPTY);
//        Item enemyItem = heldStack.getItem();
//        Monstervania.LOGGER.debug("Enemy item is: " + enemyItem.toString());

        for(int i = 0; i < damageModifiers.length; i++){
            MvDamageModifier m = damageModifiers[i];
            switch(m.getType()){
                case FIRE:
                    if(source.isFireDamage()){
                        amount *= m.getMultiplier();
                    }
                    break;
                case WOOD:
                    if(enemyItem != null && enemyItem instanceof TieredItem){
                        TieredItem ti = (TieredItem)(enemyItem);
                        if(ti.getTier() instanceof ItemTier){
                            if((ItemTier)ti.getTier() == ItemTier.WOOD) {
                                amount *= m.getMultiplier();
                            }
                        }
                    }
                    break;
                case FALL:
                    if(source.damageType.equals("fall")){
                        amount *= m.getMultiplier();
                    }
                case ELECTRIC:
                    if(source.damageType.equals("lightningBolt")){
                        amount *= m.getMultiplier();
                    }
                default:
                    break;
            }
        }


        e.setAmount(amount);
    }
}
