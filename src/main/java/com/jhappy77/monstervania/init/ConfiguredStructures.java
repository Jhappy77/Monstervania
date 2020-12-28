package com.jhappy77.monstervania.init;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.util.MvSpawnCondition;
import com.jhappy77.monstervania.util.MvStructureSpawnInfo;
import com.jhappy77.monstervania.world.structures.FrostSpiderPitStructure;
import com.jhappy77.monstervania.world.structures.SphinxBaseStructure;
import com.jhappy77.monstervania.world.structures.VampireLairSmallStructure;
import com.jhappy77.monstervania.world.structures.VampireTowerStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConfiguredStructures {


    /** Boilerplate code: Replace MY_STRUCTURE_COOL with your actual structure name


     public static StructureFeature<?, ?> CONFIGURED_MY_STRUCTURE_COOL = UnconfiguredStructures.MY_STRUCTURE_COOL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

     Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_my_structure_cool"), CONFIGURED_MY_STRUCTURE_COOL);

     */


    public static Map<StructureFeature<?, ?>, List<MvSpawnCondition<MvStructureSpawnInfo>>> configuredStructureSpawnList = new HashMap<>();


    /** Template
     public static StructureFeature<?, ?> CONFIGURED_MY_STRUCTURE =
     UnconfiguredStructures.MY_STRUCTURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
     */

    // Static instance of our structure so we can reference it and add it to biomes easily.
        public static StructureFeature<?, ?> CONFIGURED_VAMPIRE_TOWER = UnconfiguredStructures.VAMPIRE_TOWER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
        public static StructureFeature<?, ?> CONFIGURED_FROST_SPIDER_PIT = UnconfiguredStructures.FROST_SPIDER_PIT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
        public static StructureFeature<?, ?> CONFIGURED_VAMPIRE_LAIR_SMALL =
            UnconfiguredStructures.VAMPIRE_LAIR_SMALL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
        public static StructureFeature<?, ?> CONFIGURED_SPHINX_BASE =
            UnconfiguredStructures.SPHINX_BASE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);


    /*
         * Registers the configured structure which is what gets added to the biomes.
         * Noticed we are not using a forge registry because there is none for configured structures
         */
        public static void registerConfiguredStructures() {
            Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
            Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_vampire_tower"), CONFIGURED_VAMPIRE_TOWER);
            Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_frost_spider_pit"), CONFIGURED_FROST_SPIDER_PIT);
            Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_vampire_lair_small"),
                    CONFIGURED_VAMPIRE_LAIR_SMALL);
            Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_sphinx_base"),
                    CONFIGURED_SPHINX_BASE);

            /**
             Template:
             Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_my_structure!!"),
             CONFIGURED_MY_STRUCTURE);
             */

            // Add structures to spawn list map: this will auto add them to the spawn lists for the correct biomes
            addToSpawnList(CONFIGURED_VAMPIRE_TOWER, VampireTowerStructure.spawnConditions);
            addToSpawnList(CONFIGURED_FROST_SPIDER_PIT, FrostSpiderPitStructure.spawnConditions);
            addToSpawnList(CONFIGURED_VAMPIRE_LAIR_SMALL, VampireLairSmallStructure.spawnConditions);
            addToSpawnList(CONFIGURED_SPHINX_BASE, SphinxBaseStructure.spawnConditions);

            /**
             Template:
             addToSpawnList(CONFIGURED_MY_STRUCTURE, MyStructure.spawnConditions);
             */

            // Ok so, this part may be hard to grasp but basically, just add your structure to this to
            // prevent any sort of crash or issue with other mod's custom ChunkGenerators. If they use
            // FlatGenerationSettings.STRUCTURES in it and you don't add your structure to it, the game
            // could crash later when you attempt to add the StructureSeparationSettings to the dimension.

            // (It would also crash with superflat worldtype if you omit the below line
            //  and attempt to add the structure's StructureSeparationSettings to the world)
            //
            // Note: If you want your structure to spawn in superflat, remove the FlatChunkGenerator check
            // in StructureTutorialMain.addDimensionalSpacing and then create a superflat world, exit it,
            // and re-enter it and your structures will be spawning. I could not figure out why it needs
            // the restart but honestly, superflat is really buggy and shouldn't be you main focus in my opinion.
            //FlatGenerationSettings.STRUCTURES.put(UnconfiguredStructures.VAMPIRE_TOWER, CONFIGURED_VAMPIRE_TOWER);
        }

    /**
     * Adds a structure and its spawn conditions to the configured spawn list - this will be used for deciding where to
     * @param structureFeature
     * @param spawnConditionList
     */
    public static void addToSpawnList(StructureFeature<?, ?> structureFeature, List<MvSpawnCondition<MvStructureSpawnInfo>> spawnConditionList){
            configuredStructureSpawnList.put(structureFeature, spawnConditionList);
        }

}


// Now that you're done, dont forget to add code in Monstervania.java!!