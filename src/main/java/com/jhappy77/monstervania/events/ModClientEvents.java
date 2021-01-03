package com.jhappy77.monstervania.events;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.entities.VampireEntity;
import com.jhappy77.monstervania.util.MvEntitySpawnable;
import com.jhappy77.monstervania.util.MvSpawnCondition;
import com.jhappy77.monstervania.util.RegistryHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Monstervania.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent b){
        BiomeLoadReaction.addMobSpawns(b);
        BiomeLoadReaction.addStructures(b);

        //Monstervania.LOGGER.debug("Biome loading name: " + b.getName());
        //Monstervania.LOGGER.debug("Prairie: " + Biomes.PLAINS.getRegistryName() + Biomes.PLAINS.getRegistryName().toString() + Biomes.PLAINS.getLocation());
        //[23:28:06] [Netty Local Client IO #0/DEBUG] [co.jh.mo.Monstervania/]: Biome loading name: minecraft:snowy_taiga
        //[23:28:06] [Netty Local Client IO #0/DEBUG] [co.jh.mo.Monstervania/]: Prairie: minecraft:worldgen/biomeminecraft:worldgen/biomeminecraft:plains

    }

    /**
     * An event which fires to check if a mob is allowed to spawn naturally.
     * We will check entities to see if they are MvEntitySpawnables. In that case, we should check to ensure
     * that the location and world where the entities are spawning is allowed.
     * @param l
     */
    @SubscribeEvent
    public static void onMobSpawn(LivingSpawnEvent.CheckSpawn l){
        LivingEntity livingEntity = l.getEntityLiving();
        if(livingEntity instanceof MvEntitySpawnable) {
            // Only apply spawn restrictions to mobs spawning naturally or from chunk generation
            if(l.getSpawnReason() == SpawnReason.NATURAL || l.getSpawnReason() == SpawnReason.CHUNK_GENERATION) {
                BlockPos position = new BlockPos(l.getX(), l.getY(), l.getZ());
                //Monstervania.LOGGER.info("Assessing if " + livingEntity.getEntityString() + " can spawn at " + position.toString());
                MvEntitySpawnable spawnableEntity = (MvEntitySpawnable) livingEntity;
                IWorld world = l.getWorld();

                boolean canSpawn = false;
                for (MvSpawnCondition cond : spawnableEntity.getSpawnConditions()) {
                    if (cond.evaluateWorldClauses(world, position)) {
                        canSpawn = true;
                        break;
                    }
                }
                // If the mob hasn't gotten approval to spawn naturally, cancel the spawning event!
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

        //Monstervania.LOGGER.info("Attack entity event");

        LivingEntity entityLiving = event.getEntityLiving();
        if(entityLiving instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity)entityLiving;
            //Monstervania.LOGGER.info("Player attacked!");
            if(event.getTarget() instanceof VampireEntity){
                //Monstervania.LOGGER.info("Player attacked vampire!");
            }
        }

        else if(entityLiving instanceof VampireEntity){
           // Monstervania.LOGGER.info("Get entity living: vampire");
            if(event.getTarget().isAlive()){
                LivingEntity target = (LivingEntity) event.getTarget();
                entityLiving.addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH, 10,0));

                //Monstervania.LOGGER.info("Player was attacked by vamp!");
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
