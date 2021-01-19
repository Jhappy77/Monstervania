package com.jhappy77.monstervania.world.structures;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.util.MvSpawnCondition;
import com.jhappy77.monstervania.util.MvStructureSpawnInfo;
import com.jhappy77.monstervania.util.MvStructureSpawnable;
import com.mojang.serialization.Codec;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;

public class SphinxStructure extends Structure<NoFeatureConfig> implements MvStructureSpawnable {
        public SphinxStructure(Codec<NoFeatureConfig> codec) {
            super(codec);
        }

        /**
         * This is how the worldgen code knows what to call when it
         * is time to create the pieces of the structure for generation.
         */
        @Override
        public  IStartFactory<NoFeatureConfig> getStartFactory() {
            return SphinxStructure.Start::new;
        }


        /**
         * Generation stage for when to generate the structure. there are 10 stages you can pick from!
         * This surface structure stage places the structure before plants and ores are generated.
         */
        @Override
        public GenerationStage.Decoration getDecorationStage() {
            return GenerationStage.Decoration.SURFACE_STRUCTURES;
        }


        /**
         * || ONLY WORKS IN FORGE 34.1.12+ ||
         *
         * This method allows us to have mobs that spawn naturally over time in our structure.
         * No other mobs will spawn in the structure of the same entity classification.
         * The reason you want to match the classifications is so that your structure's mob
         * will contribute to that classification's cap. Otherwise, it may cause a runaway
         * spawning of the mob that will never stop.
         *
         * NOTE: getDefaultSpawnList is for monsters only and getDefaultCreatureSpawnList is
         *       for creatures only. If you want to add entities of another classification,
         *       use the StructureSpawnListGatherEvent to add water_creatures, water_ambient,
         *       ambient, or misc mobs. Use that event to add/remove mobs from structures
         *       that are not your own.
         */
//        private static final List<MobSpawnInfo.Spawners> STRUCTURE_MONSTERS = ImmutableList.of(
//                new MobSpawnInfo.Spawners(EntityType.ILLUSIONER, 100, 4, 9),
//                new MobSpawnInfo.Spawners(EntityType.VINDICATOR, 100, 4, 9)
//        );
//        @Override
//        public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {
//            return STRUCTURE_MONSTERS;
//        }
//
//        private static final List<MobSpawnInfo.Spawners> STRUCTURE_CREATURES = ImmutableList.of(
//                new MobSpawnInfo.Spawners(EntityType.SHEEP, 30, 10, 15),
//                new MobSpawnInfo.Spawners(EntityType.RABBIT, 100, 1, 2)
//        );
//        @Override
//        public List<MobSpawnInfo.Spawners> getDefaultCreatureSpawnList() {
//            return STRUCTURE_CREATURES;
//        }


        /*
         * This is where extra checks can be done to determine if the structure can spawn here.
         * This only needs to be overridden if you're adding additional spawn conditions.
         *
         * Notice how the biome is also passed in. Though, you are not going to
         * do any biome checking here as you should've added this structure to
         * the biomes you wanted already with the biome load event.
         *
         * Basically, this method is used for determining if the land is at a suitable height,
         * if certain other structures are too close or not, or some other restrictive condition.
         *
         * For example, Pillager Outposts added a check to make sure it cannot spawn within 10 chunk of a Village.
         * (Bedrock Edition seems to not have the same check)
         *
         *
         * Also, please for the love of god, do not do dimension checking here. If you do and
         * another mod's dimension is trying to spawn your structure, the locate
         * command will make minecraft hang forever and break the game.
         *
         * Instead, use the addDimensionalSpacing method in StructureTutorialMain class.
         * If you check for the dimension there and do not add your structure's
         * spacing into the chunk generator, the structure will not spawn in that dimension!
         */
//    @Override
//    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
//        int landHeight = chunkGenerator.getNoiseHeight(chunkX << 4, chunkZ << 4, Heightmap.Type.WORLD_SURFACE_WG);
//        return landHeight > 100;
//    }


        /**
         * Handles calling up the structure's pieces class and height that structure will spawn at.
         */
        public static class Start extends StructureStart<NoFeatureConfig> {
            public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
                super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
            }

            @Override
            public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {

                // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
                int x = (chunkX << 4) + 7;
                int z = (chunkZ << 4) + 7;

                /*
                 * We pass this into func_242837_a to tell it where to generate the structure.
                 * If func_242837_a's last parameter is true, blockpos's Y value is ignored and the
                 * structure will spawn at terrain height instead. Set that parameter to false to
                 * force the structure to spawn at blockpos's Y value instead. You got options here!
                 */
                BlockPos blockpos = new BlockPos(x, 0, z);

                // All a structure has to do is call this method to turn it into a jigsaw based structure!
                JigsawManager.func_242837_a(
                        dynamicRegistryManager,
                        new VillageConfig(() -> dynamicRegistryManager.getRegistry(Registry.JIGSAW_POOL_KEY)
                                // The path to the starting Template Pool JSON file to read.
                                //
                                // Note, this is "structure_tutorial:run_down_house/start_pool" which means
                                // the game will automatically look into the following path for the template pool:
                                // "resources/data/structure_tutorial/worldgen/template_pool/run_down_house/start_pool.json"
                                // This is why your pool files must be in "data/<modid>/worldgen/template_pool/<the path to the pool here>"
                                // because the game automatically will check in worldgen/template_pool for the pools.
                                .getOrDefault(new ResourceLocation(Monstervania.MOD_ID, "sphinx/start_pool")),

                                // How many pieces outward from center can a recursive jigsaw structure spawn.
                                // Our structure is only 1 piece outward and isn't recursive so any value of 1 or more doesn't change anything.
                                // However, I recommend you keep this a decent value like 10 so people can use datapacks to add additional pieces to your structure easily.
                                // But don't make it too large for recursive structures like villages or you'll crash server due to hundreds of pieces attempting to generate!
                                10),
                        AbstractVillagePiece::new,
                        chunkGenerator,
                        templateManagerIn,
                        blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
                        this.components, // The list that will be populated with the jigsaw pieces after this method.
                        this.rand,
                        true, // Allow intersecting jigsaw pieces. If false, villages cannot generate houses. I recommend to keep this to true.
                        true); // Place at heightmap (top land). Set this to false for structure to be place at blockpos's Y value instead


                //Reference for these lines: https://github.com/TelepathicGrunt/StructureTutorialMod/blob/1.16.3-Forge-jigsaw/src/main/java/com/telepathicgrunt/structuretutorial/structures/RunDownHouseStructure.java
                // Lines 184, 185

                // Lowers entire structure by 1 block
                //this.components.forEach(piece -> piece.offset(0, -1, 0));
                // Raises the bounding box for transformSurroundingLand, causing it to allow land transform to bury it
                //this.components.forEach(piece -> piece.getBoundingBox().minY += 1);


                // Sets the bounds of the structure once you are finished.
                this.recalculateStructureSize();

                // I use to debug and quickly find out if the structure is spawning or not and where it is.
                // This is returning the coordinates of the center starting piece.
                Monstervania.LOGGER.log(Level.DEBUG, "Sphinx at " +
                        this.components.get(0).getBoundingBox().minX + " " +
                        this.components.get(0).getBoundingBox().minY + " " +
                        this.components.get(0).getBoundingBox().minZ);
            }

        }

    // Structure Spawn Info - Used to determine which biomes to add this structure to!
    @Override
    public List<MvSpawnCondition<MvStructureSpawnInfo>> getSpawnConditions() {
        return spawnConditions;
    }

    public static List<MvSpawnCondition<MvStructureSpawnInfo>> spawnConditions = new ArrayList<MvSpawnCondition<MvStructureSpawnInfo>>();

    //TODO: Change every time you add a new structure!
    static {
        spawnConditions.add(new MvSpawnCondition<>(new MvStructureSpawnInfo()).restrictToLand().restrictToOverworld()
                .addBiomeSpawnClause(new MvSpawnCondition.BiomeCategorySpawnClause().addCategory(Biome.Category.DESERT))
        );
    }

}
