package com.jhappy77.monstervania.events;

import com.jhappy77.monstervania.init.ConfiguredStructures;
import com.jhappy77.monstervania.init.ModEntityTypes;
import com.jhappy77.monstervania.util.MvMobSpawnInfo;
import com.jhappy77.monstervania.util.MvSpawnCondition;
import com.jhappy77.monstervania.util.MvStructureSpawnInfo;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class BiomeLoadReaction {

    public static Collection<RegistryObject<EntityType<?>>> ENTITY_TYPES = ModEntityTypes.ENTITY_TYPES.getEntries();


    // Iterate through every mob that is registered to be able to naturally spawn
    // If it can spawn in this biome, add it to the list of spawnable mobs for this biome
    public static void addMobSpawns(BiomeLoadingEvent b){

        for(Map.Entry<String, List<MvSpawnCondition<MvMobSpawnInfo>>> entry: ModEntityTypes.spawnables.entrySet()){
            // For every spawn condition associated with this monster
            for(MvSpawnCondition<MvMobSpawnInfo> spawnCondition : entry.getValue()){
                // If the spawn condition is valid in this biome
                if(spawnCondition.evaluateBiomeSpawnClauses(b)){
                    RegistryObject obj = ModEntityTypes.registrymap.get(entry.getKey());
                    EntityType t = (EntityType)obj.get();
                    addMob(spawnCondition.getSpawnInformation(), t, b);
                }
            }
        }
    }

    private static void addMob(MvMobSpawnInfo spawnInfo, EntityType t, BiomeLoadingEvent b){
        b.getSpawns().withSpawner(t.getClassification(), new MobSpawnInfo.Spawners(t, spawnInfo.getWeight(), spawnInfo.getMincount(), spawnInfo.getMaxcount()));
    }

    public static void addStructures(BiomeLoadingEvent b){

        // Iterate through every structure feature registered to spawn based on their spawn conditions.
        // If it can spawn in this biome, add it to the list of configured structures.

        for(Map.Entry<StructureFeature<?, ?>, List<MvSpawnCondition<MvStructureSpawnInfo>>> entry: ConfiguredStructures.configuredStructureSpawnList.entrySet()){
            // For every spawn condition associated with this structure:
            for(MvSpawnCondition<MvStructureSpawnInfo> spawnCondition : entry.getValue()) {
                // Check if the spawn condition is valid in this biome
                if (spawnCondition.evaluateBiomeSpawnClauses(b)) {
                    // Add this structure to the list of structures that can be generated in the biome
                    b.getGeneration().getStructures().add(()-> entry.getKey());
                    break;
                }
            }
        }

    }


    public static class creeperRemovePredicate implements Predicate<MobSpawnInfo.Spawners> {

        @Override
        public boolean test(MobSpawnInfo.Spawners spawners) {
            if(spawners.type == EntityType.CREEPER){
                return true;
            }
            return false;
        }
    }


    public static void makeVanillaAlterations(BiomeLoadingEvent event){
        if(event.getCategory() == Biome.Category.DESERT){
            event.getSpawns().getSpawner(EntityClassification.MONSTER).removeIf(new creeperRemovePredicate());
        }
    }



}
