package com.jhappy77.monstervania.events;

import com.jhappy77.monstervania.init.ConfiguredStructures;
import com.jhappy77.monstervania.init.ModEntityTypes;
import com.jhappy77.monstervania.util.MvSpawnCondition;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BiomeLoadReaction {

    public static Collection<RegistryObject<EntityType<?>>> ENTITY_TYPES = ModEntityTypes.ENTITY_TYPES.getEntries();

    public static void addMobSpawns(BiomeLoadingEvent b){


        for(Map.Entry<String, List<MvSpawnCondition>> entry: ModEntityTypes.spawnables.entrySet()){
            // For every spawn condition associated with this monster
            for(MvSpawnCondition spawnCondition : entry.getValue()){
                // If the spawn condition is valid in this biome
                if(spawnCondition.evaluateBiomeSpawnClauses(b)){
                    RegistryObject obj = ModEntityTypes.registrymap.get(entry.getKey());
                    EntityType t = (EntityType)obj.get();
                    addMob(spawnCondition, t, b);
                }
            }

            //List<MvSpawnCondition> spawnconditions = ModEntityTypes.spawnables
            //ModEntityTypes.ENTITY_TYPES.FRANKENGOLEM.get();
        }


        //b.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(ModEntityTypes.FRANKENGOLEM.get(), 20, 1, 1));
        //addSpawnsByCategory(b);
    }

    private static void addMob(MvSpawnCondition mvsc, EntityType t, BiomeLoadingEvent b){
        b.getSpawns().withSpawner(t.getClassification(), new MobSpawnInfo.Spawners(t, mvsc.getWeight(), mvsc.getMincount(), mvsc.getMaxcount()));
    }

    public static void addStructures(BiomeLoadingEvent b){
        b.getGeneration().getStructures().add(()-> ConfiguredStructures.CONFIGURED_VAMPIRE_TOWER);
        b.getGeneration().getStructures().add(()-> ConfiguredStructures.CONFIGURED_FROST_SPIDER_PIT);
    }

}
