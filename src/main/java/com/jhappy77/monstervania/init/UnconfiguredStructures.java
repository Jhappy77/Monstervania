package com.jhappy77.monstervania.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.world.structures.FrostSpiderPitStructure;
import com.jhappy77.monstervania.world.structures.VampireLairSmallStructure;
import com.jhappy77.monstervania.world.structures.VampireTowerStructure;
import com.jhappy77.monstervania.world.structures.pieces.FrostSpiderPitPieces;
import com.jhappy77.monstervania.world.structures.pieces.VampireLairSmallPieces;
import com.jhappy77.monstervania.world.structures.pieces.VampireTowerPieces;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.event.RegistryEvent;


//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Monstervania.MOD_ID)
public class UnconfiguredStructures {

    // Used to generate unique seeds for every structure
    private static int STRUCTURE_SEED = 667711991;


    /** Template
     public static Structure<NoFeatureConfig> TEMPLATE_STRUCT = new MyTemplateStructure(NoFeatureConfig.field_236558_a_);
     public static IStructurePieceType TEMPLATE_STRUCT_PIECE = MyTemplateStructurePieces.Piece::new;
     */


    // Vampire Tower
    public static Structure<NoFeatureConfig> VAMPIRE_TOWER = new VampireTowerStructure(NoFeatureConfig.field_236558_a_);
    public static IStructurePieceType VAMPIRE_TOWER_PIECE = VampireTowerPieces.Piece::new;

    // Frost Spider Pit
    public static Structure<NoFeatureConfig> FROST_SPIDER_PIT = new FrostSpiderPitStructure(NoFeatureConfig.field_236558_a_);
    public static IStructurePieceType FROST_SPIDER_PIT_PIECE = FrostSpiderPitPieces.Piece::new;

    // Small Vampire Lair
    public static Structure<NoFeatureConfig> VAMPIRE_LAIR_SMALL = new VampireLairSmallStructure(NoFeatureConfig.field_236558_a_);
    public static IStructurePieceType VAMPIRE_LAIR_SMALL_PIECE = VampireLairSmallPieces.Piece::new;

    // Basic Sphinx
//    public static Structure<NoFeatureConfig> SPHINX_BASE = new SphinxBaseStructure(NoFeatureConfig.field_236558_a_);
//    public static IStructurePieceType SPHINX_BASE_PIECE = SphinxBasePieces.Piece::new;

    /*
     * Registers the structure along with path
     */
    public static void registerStructures(RegistryEvent.Register<Structure<?>> event) {

        /*** Template
         Monstervania.register(event.getRegistry(), TEMPLATE_STRUCTA, "template_structure");
         */

        // Registers the structure (our helper method attaches the modid to the front of the Structure's ResourceLocation)
        Monstervania.register(event.getRegistry(), VAMPIRE_TOWER, "vampire_tower");
        Monstervania.register(event.getRegistry(), FROST_SPIDER_PIT, "frost_spider_pit");
        Monstervania.register(event.getRegistry(), VAMPIRE_LAIR_SMALL, "vampire_lair_small");
        //Monstervania.register(event.getRegistry(), SPHINX_BASE, "sphinx_base");

        /** Template
        registerStructure(TEMPLATE_STRUCTA, new StructureSeparationSettings(20, 5, STRUCTURE_SEED++),
         false);
         */

        /*
         * Do not change names of structures or else they will be corrupted
         */
        registerStructure(
                VAMPIRE_TOWER, /* The instance of the structure */
                new StructureSeparationSettings(30 /* maximum distance apart in chunks between spawn attempts */,
                        10 /* minimum distance apart in chunks between spawn attempts */,
                         STRUCTURE_SEED++/* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                false);

        registerStructure(
                FROST_SPIDER_PIT,
                new StructureSeparationSettings(30,
                        10,
                        STRUCTURE_SEED++),
                    true);

        registerStructure(VAMPIRE_LAIR_SMALL, new StructureSeparationSettings(20, 5, STRUCTURE_SEED++),
                false);

//        registerStructure(SPHINX_BASE, new StructureSeparationSettings(20, 5, STRUCTURE_SEED++),
//                true);

        registerAllPieces();
    }


    /*
     * Adds the provided structure to the registry, and adds the separation settings.
     * The rarity of the structure is determined based on the values passed into
     * this method in the structureSeparationSettings argument. Called by registerFeatures.
     */
    public static <F extends Structure<?>> void registerStructure(
            F structure,
            StructureSeparationSettings structureSeparationSettings,
            boolean transformSurroundingLand)
    {

        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        /*
         * Will add land at the base of the structure like it does for Villages and Outposts.
         * Doesn't work well on structure that have pieces stacked vertically.
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
         * correct in any dimension or worldtype instead of not spawning. Use the WorldEvent.Load event if not working in a particular dimension.
         */
        DimensionStructuresSettings.field_236191_b_ =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.field_236191_b_)
                        .put(structure, structureSeparationSettings)
                        .build();
    }

    /*
     * Registers all structure pieces
     */
    public static void registerAllPieces() {
        /** Template
         registerStructurePiece(MY_TEMPLATE_STRUCT_PIECE, new ResourceLocation(Monstervania.MOD_ID, "my_template_struct_piece"));
         */

        registerStructurePiece(VAMPIRE_TOWER_PIECE, new ResourceLocation(Monstervania.MOD_ID, "vampire_tower_piece"));
        registerStructurePiece(FROST_SPIDER_PIT_PIECE, new ResourceLocation(Monstervania.MOD_ID, "frost_spider_pit_piece"));
        registerStructurePiece(VAMPIRE_LAIR_SMALL_PIECE, new ResourceLocation(Monstervania.MOD_ID, "vampire_lair_small_piece"));
        //registerStructurePiece(SPHINX_BASE_PIECE, new ResourceLocation(Monstervania.MOD_ID, "sphinx_base_piece"));
    }

    /*
     * Registers the structures pieces themselves. If you don't do this part, Forge will complain to
     * you in the Console. Called by registerPieces.
     */
    static void registerStructurePiece(IStructurePieceType structurePiece, ResourceLocation rl) {
        Registry.register(Registry.STRUCTURE_PIECE, rl, structurePiece);
    }

}
