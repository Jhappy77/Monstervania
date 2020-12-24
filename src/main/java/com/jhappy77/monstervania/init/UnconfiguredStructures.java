package com.jhappy77.monstervania.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.world.structures.FrostSpiderPitStructure;
import com.jhappy77.monstervania.world.structures.VampireTowerStructure;
import com.jhappy77.monstervania.world.structures.pieces.FrostSpiderPitPieces;
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

    // Vampire Tower
    public static Structure<NoFeatureConfig> VAMPIRE_TOWER = new VampireTowerStructure(NoFeatureConfig.field_236558_a_);
    public static IStructurePieceType VAMPIRE_TOWER_PIECE = VampireTowerPieces.Piece::new;

    // Frost Spider Pit
    public static Structure<NoFeatureConfig> FROST_SPIDER_PIT = new FrostSpiderPitStructure(NoFeatureConfig.field_236558_a_);
    public static IStructurePieceType FROST_SPIDER_PIT_PIECE = FrostSpiderPitPieces.Piece::new;


    /*
     * Registers the structure along with path
     */
    public static void registerStructures(RegistryEvent.Register<Structure<?>> event) {

        // Registers the structure (our helper method attaches the modid to the front of the Structure's ResourceLocation)
        Monstervania.register(event.getRegistry(), VAMPIRE_TOWER, "vampire_tower");
        Monstervania.register(event.getRegistry(), FROST_SPIDER_PIT, "frost_spider_pit");

        /*
         * Do not change names of structures or else they will be corrupted
         */
        registerStructure(
                VAMPIRE_TOWER, /* The instance of the structure */
                new StructureSeparationSettings(30 /* maximum distance apart in chunks between spawn attempts */,
                        10 /* minimum distance apart in chunks between spawn attempts */,
                        667711991 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                false);

        registerStructure(
                FROST_SPIDER_PIT,
                new StructureSeparationSettings(30,
                        10,
                        667711992),
                    false);

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
        registerStructurePiece(VAMPIRE_TOWER_PIECE, new ResourceLocation(Monstervania.MOD_ID, "vampire_tower_piece"));
        registerStructurePiece(FROST_SPIDER_PIT_PIECE, new ResourceLocation(Monstervania.MOD_ID, "frost_spider_pit_piece"));
    }

    /*
     * Registers the structures pieces themselves. If you don't do this part, Forge will complain to
     * you in the Console. Called by registerPieces.
     */
    static void registerStructurePiece(IStructurePieceType structurePiece, ResourceLocation rl) {
        Registry.register(Registry.STRUCTURE_PIECE, rl, structurePiece);
    }

}
