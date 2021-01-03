package com.jhappy77.monstervania.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.world.structures.IcePillarStructure;
import com.jhappy77.monstervania.world.structures.SkeletalFountainStructure;
import com.jhappy77.monstervania.world.structures.SlimeCaveSmallStructure;
import com.jhappy77.monstervania.world.structures.SphinxStructure;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class JigsawStructures {

    private static int SEED = 666772990;

    public static final DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Monstervania.MOD_ID);


    public static final RegistryObject<Structure<NoFeatureConfig>> SPHINX = registerStructure("sphinx", () -> (new SphinxStructure(NoFeatureConfig.field_236558_a_)));
    public static final RegistryObject<Structure<NoFeatureConfig>> ICE_PILLAR = registerStructure("ice_pillar", () -> (new IcePillarStructure(NoFeatureConfig.field_236558_a_)));
    public static final RegistryObject<Structure<NoFeatureConfig>> SKELETAL_FOUNTAIN = registerStructure("skeletal_fountain", () -> (new SkeletalFountainStructure(NoFeatureConfig.field_236558_a_)));
    public static final RegistryObject<Structure<NoFeatureConfig>> SLIME_CAVE_SMALL = registerStructure("slime_cave_small", () -> (new SlimeCaveSmallStructure(NoFeatureConfig.field_236558_a_)));

    /** Template
     public static final RegistryObject<Structure<NoFeatureConfig>> SPHINX = registerStructure("cHanGeMe", () -> (new CoolStructa(NoFeatureConfig.field_236558_a_)));
     */

    public static void setupStructures() {
        setupMapSpacingAndLand(
                SPHINX.get(), /* The instance of the structure */
                new StructureSeparationSettings(20 /* maximum distance apart in chunks between spawn attempts */,
                        10 /* minimum distance apart in chunks between spawn attempts */,
                        SEED++ /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                true);


        setupMapSpacingAndLand(
            ICE_PILLAR.get(), new StructureSeparationSettings(20 /*max*/ , 10 /* min */,  SEED++ /* seed */),
                false);

        setupMapSpacingAndLand(SKELETAL_FOUNTAIN.get(), new StructureSeparationSettings(20, 15, SEED++), false);

        setupMapSpacingAndLand(SLIME_CAVE_SMALL.get(), new StructureSeparationSettings(20, 10, SEED++), true);

        /* Template
        setupMapSpacingAndLand(COOL_STRUCTURE.get(), new StructureSeparationSettings(20, 10, SEED++), true);
        */
    }



    private static <T extends Structure<?>> RegistryObject<T> registerStructure(String name, Supplier<T> structure) {
        return DEFERRED_REGISTRY_STRUCTURE.register(name, structure);
    }

    /**
     * Adds the provided structure to the registry, and adds the separation settings.
     * The rarity of the structure is determined based on the values passed into
     * this method in the structureSeparationSettings argument. Called by registerFeatures.
     */
    public static <F extends Structure<?>> void setupMapSpacingAndLand(
            F structure,
            StructureSeparationSettings structureSeparationSettings,
            boolean transformSurroundingLand)
    {
        /*
         * We need to add our structures into the map in Structure alongside vanilla
         * structures or else it will cause errors. Called by registerStructure.
         *
         * If the registration is setup properly for the structure,
         * getRegistryName() should never return null.
         */
        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        /*
         * Whether surrounding land will be modified automatically to conform to the bottom of the structure.
         * Basically, it adds land at the base of the structure like it does for Villages and Outposts.
         * Doesn't work well on structure that have pieces stacked vertically or change in heights.
         *
         * Note: The air space this method will create will be filled with water if the structure is below sealevel.
         * This means this is best for structure above sealevel so keep that in mind.
         */
        if(transformSurroundingLand){
            Structure.field_236384_t_ =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.field_236384_t_)
                            .add(structure)
                            .build();
        }

        /*
         * Adds the structure's spacing into several places so that the structure's spacing remains
         * correct in any dimension or worldtype instead of not spawning.
         *
         * However, it seems it doesn't always work for code made dimensions as they read from
         * this list beforehand. Use the WorldEvent.Load event in StructureTutorialMain to add
         * the structure spacing from this list into that dimension.
         */
        DimensionStructuresSettings.field_236191_b_ =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.field_236191_b_)
                        .put(structure, structureSeparationSettings)
                        .build();
    }

}
