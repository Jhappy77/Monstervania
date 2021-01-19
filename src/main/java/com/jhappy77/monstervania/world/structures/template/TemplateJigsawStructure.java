package com.jhappy77.monstervania.world.structures.template;

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
import net.minecraft.world.gen.Heightmap;
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
/*
public class TemplateJigsawStructure extends Structure<NoFeatureConfig> implements MvStructureSpawnable {

    public TemplateJigsawStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public  IStartFactory<NoFeatureConfig> getStartFactory() {
        return this.Start::new;
    }


    //TODO: Check every time you add a structure!
    @Override
    public GenerationStage.Decoration getDecorationStage() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }


    // Mob spawning

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
//    /mob spawning ends


    //Extra checks to determine spawnability
//    @Override
//    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
//        int landHeight = chunkGenerator.getNoiseHeight(chunkX << 4, chunkZ << 4, Heightmap.Type.WORLD_SURFACE_WG);
//        return landHeight > 100;
//    }


    //Handles calling up the structure's pieces class and height that structure will spawn at.
    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {

            // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;

            //TODO: Decide height of structure
            //int y = chunkGenerator.getHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG)
            BlockPos blockpos = new BlockPos(x, 0, z);




            // All a structure has to do is call this method to turn it into a jigsaw based structure!
            JigsawManager.func_242837_a(
                    dynamicRegistryManager,
                    new VillageConfig(() -> dynamicRegistryManager.getRegistry(Registry.JIGSAW_POOL_KEY)
                            //TODO: Change every time you add a structure!
                            .getOrDefault(new ResourceLocation(Monstervania.MOD_ID, "sphinx/start_pool")),
                            // Recursivity
                            10),
                    AbstractVillagePiece::new,
                    chunkGenerator,
                    templateManagerIn,
                    blockpos, // Position of the structure
                    this.components, // The list that will be populated with the jigsaw pieces after this method.
                    this.rand,
                    true, // Allow intersecting jigsaw pieces (recommended: true)
                    true); // Place at surface. Set to false for structure to be place at blockpos's Y instead


            //Reference for these lines: https://github.com/TelepathicGrunt/StructureTutorialMod/blob/1.16.3-Forge-jigsaw/src/main/java/com/telepathicgrunt/structuretutorial/structures/RunDownHouseStructure.java
            // Lines 184, 185

            // Lowers entire structure by 1 block
            //this.components.forEach(piece -> piece.offset(0, -1, 0));
            // Raises the bounding box for transformSurroundingLand, causing it to allow land transform to bury it
            this.components.forEach(piece -> piece.getBoundingBox().minY += 1);


            // Sets the bounds of the structure once you are finished.
            this.recalculateStructureSize();

            //TODO: Change every time you add a structure!
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
*/