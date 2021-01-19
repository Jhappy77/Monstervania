package com.jhappy77.monstervania.init;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.util.MvSpawnCondition;
import com.jhappy77.monstervania.util.MvStructureSpawnInfo;
import com.jhappy77.monstervania.world.structures.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConfiguredStructures {

    private static NoFeatureConfig NFC = IFeatureConfig.NO_FEATURE_CONFIG;

    // Static instance of our structure so we can reference it and add it to biomes easily.

    public static StructureFeature<?, ?> CONFIGURED_VAMPIRE_TOWER = UnconfiguredStructures.VAMPIRE_TOWER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static StructureFeature<?, ?> CONFIGURED_FROST_SPIDER_PIT = UnconfiguredStructures.FROST_SPIDER_PIT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static StructureFeature<?, ?> CONFIGURED_VAMPIRE_LAIR_SMALL =
        UnconfiguredStructures.VAMPIRE_LAIR_SMALL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);


    public static StructureFeature<?, ?> CONFIGURED_BIG_WINDMILL = JigsawStructures.BIG_WINDMILL.get().withConfiguration(NFC);
    public static StructureFeature<?, ?> CONFIGURED_SPHINX = JigsawStructures.SPHINX.get().withConfiguration(NFC);
    public static StructureFeature<?, ?> CONFIGURED_ICE_PILLAR = JigsawStructures.ICE_PILLAR.get().withConfiguration(NFC);
    public static StructureFeature<?, ?> CONFIGURED_SKELETAL_FOUNTAIN = JigsawStructures.SKELETAL_FOUNTAIN.get().withConfiguration(NFC);
    public static StructureFeature<?, ?> CONFIGURED_SLIME_CAVE_SMALL = JigsawStructures.SLIME_CAVE_SMALL.get().withConfiguration(NFC);
    public static StructureFeature<?, ?> CONFIGURED_WITCHES_KNOB = JigsawStructures.WITCHES_KNOB.get().withConfiguration(NFC);

    /** Template
     public static StructureFeature<?, ?> CONFIGURED_MY_STRUCTURE = JigsawStructures.COOLIO.get().withConfiguration(NFC);
     */



        /*
         * Registers the configured structure (what gets added in worldgen)
         */
        public static void registerConfiguredStructures() {
            Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
            Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_vampire_tower"), CONFIGURED_VAMPIRE_TOWER);
            Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_frost_spider_pit"), CONFIGURED_FROST_SPIDER_PIT);
            Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_vampire_lair_small"),
                    CONFIGURED_VAMPIRE_LAIR_SMALL);

            Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_big_windmill"),
                    CONFIGURED_BIG_WINDMILL);
            Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_ice_pillar"),
                    CONFIGURED_ICE_PILLAR);
            Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_skeletal_fountain"),
                    CONFIGURED_SKELETAL_FOUNTAIN);
            Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_slime_cave_small"),
                    CONFIGURED_SLIME_CAVE_SMALL);
            Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_sphinx"), CONFIGURED_SPHINX);
            Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_witches_knob"),
                    CONFIGURED_WITCHES_KNOB);

            /**
             Template:
             Registry.register(registry, new ResourceLocation(Monstervania.MOD_ID, "configured_my_structure!!"),
             CONFIGURED_MY_STRUCTURE);
             */

            // Add structures to spawn list map: this will auto add them to the spawn lists for the correct biomes
            addToSpawnList(CONFIGURED_VAMPIRE_TOWER, VampireTowerStructure.spawnConditions);
            addToSpawnList(CONFIGURED_FROST_SPIDER_PIT, FrostSpiderPitStructure.spawnConditions);
            addToSpawnList(CONFIGURED_VAMPIRE_LAIR_SMALL, VampireLairSmallStructure.spawnConditions);


            addToSpawnList(CONFIGURED_BIG_WINDMILL, BigWindmillStructure.spawnConditions);
            addToSpawnList(CONFIGURED_ICE_PILLAR, IcePillarStructure.spawnConditions);
            addToSpawnList(CONFIGURED_SKELETAL_FOUNTAIN, SkeletalFountainStructure.spawnConditions);
            addToSpawnList(CONFIGURED_SLIME_CAVE_SMALL, SlimeCaveSmallStructure.spawnConditions);
            addToSpawnList(CONFIGURED_SPHINX, SphinxStructure.spawnConditions);
            addToSpawnList(CONFIGURED_WITCHES_KNOB, WitchesKnobStructure.spawnConditions);

            /**
             Template:
             addToSpawnList(CONFIGURED_MY_STRUCTURE, MyStructure.spawnConditions);
             */

            // Everywhere past here is util and doesn't need to be touched

            // Telepathic Grunt's advice:
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

    // Used for keeping track of spawn conditions for each structure
    public static Map<StructureFeature<?, ?>, List<MvSpawnCondition<MvStructureSpawnInfo>>> configuredStructureSpawnList = new HashMap<>();

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