package com.jhappy77.monstervania.events;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.entities.VampireEntity;
import com.jhappy77.monstervania.util.MvSpawnCondition;
import com.jhappy77.monstervania.util.MvSpawnable;
import com.jhappy77.monstervania.util.RegistryHandler;
import com.mojang.brigadier.context.CommandContextBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Monstervania.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent b){
        BiomeLoadReaction.addMobSpawns(b);
        BiomeLoadReaction.addStructures(b);
    }

    @SubscribeEvent
    public static void onMobSpawn(LivingSpawnEvent.CheckSpawn l){
        LivingEntity livingEntity = l.getEntityLiving();
        if(livingEntity instanceof MvSpawnable) {
            // Only apply spawn restrictions to mobs spawning naturally or from chunk generation
            if(l.getSpawnReason() == SpawnReason.NATURAL || l.getSpawnReason() == SpawnReason.CHUNK_GENERATION) {
                BlockPos position = new BlockPos(l.getX(), l.getY(), l.getZ());
                //Monstervania.LOGGER.info("Assessing if " + livingEntity.getEntityString() + " can spawn at " + position.toString());
                MvSpawnable spawnableEntity = (MvSpawnable) livingEntity;
                IWorld world = l.getWorld();

                boolean canSpawn = false;
                for (MvSpawnCondition cond : spawnableEntity.getSpawnConditions()) {
                    if (cond.evaluateWorldClauses(world, position)) {
                        canSpawn = true;
                        break;
                    }
                }

                if (!canSpawn) {
                    //Monstervania.LOGGER.info("Cancelled spawn for " + livingEntity.getEntityString() + " at " + position.toString());
                    l.setResult(Event.Result.DENY);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTakesDamage(LivingDamageEvent event){
        LivingDamageEventReaction.process(event);
    }


    @SubscribeEvent
    public static void onVampireAttack(AttackEntityEvent event){

        Monstervania.LOGGER.info("Attack entity event");

        LivingEntity vampire = event.getEntityLiving();
        if(vampire instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity)vampire;
            Monstervania.LOGGER.info("Player attacked!");
            if(event.getTarget() instanceof VampireEntity){
                Monstervania.LOGGER.info("Player attacked vampire!");
            }
            if(player.getHeldItemMainhand().getItem() == RegistryHandler.LIGHTNING_ROD.get()){
                Monstervania.LOGGER.info("Player attacked w lightning rod!");
            }
        }

        if(vampire instanceof VampireEntity){
            Monstervania.LOGGER.info("Get entity living: vampire");
            if(event.getTarget().isAlive()){
                LivingEntity target = (LivingEntity) event.getTarget();
                vampire.addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH, 10,0));

                Monstervania.LOGGER.info("Player was attacked by vamp!");
                if(event.getPlayer().getEntityWorld().isRemote){
                    String msg = TextFormatting.RED + "You have been bitten!";
                    PlayerEntity player = event.getPlayer();
                    player.sendMessage(new StringTextComponent(msg), player.getUniqueID());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event){
        LightningEventReaction.process(event);
    }




}
