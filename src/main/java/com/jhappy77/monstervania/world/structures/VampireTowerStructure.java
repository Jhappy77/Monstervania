package com.jhappy77.monstervania.world.structures;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.world.structures.pieces.VampireTowerPieces;
import com.mojang.serialization.Codec;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.Level;

// Inspiration from https://github.com/TelepathicGrunt/StructureTutorialMod

public class VampireTowerStructure extends Structure<NoFeatureConfig> {


    public VampireTowerStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

//    /**
//     * Extra checks for if structure can spawn here
//     */
//    @Override
//    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
//        int landHeight = chunkGenerator.getNoiseHeight(chunkX << 4, chunkZ << 4, Heightmap.Type.WORLD_SURFACE_WG);
//        return landHeight > 100;
//    }

    @Override
    public String getStructureName(){
        return new ResourceLocation(Monstervania.MOD_ID, "vampire_tower").toString();
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return VampireTowerStructure.Start::new;
    }

//    protected ChunkPos getStartPositionForPosition(ChunkGenerator generator, Random rand, int chunkX, int chunkZ, int offsetX, int offsetZ){
//        // Chunks cannot be further away than 10 chunks
//        int maxDistance = 10;
//        // Chunks cannot be closer than this many chunks
//        int minDistance = 3;
//    }

    @Override
    public GenerationStage.Decoration getDecorationStage()
    {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    /**
     * Handles calling up the structure's pieces class and height that structure will spawn at.
     */
    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {

            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
            // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;
            int y = chunkGenerator.getHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);
            BlockPos blockpos = new BlockPos(x, y-3, z);

            // In this case, we offset the pieces up 1 so that the doorstep is not lower than the original
            // terrain and then we extend the bounding box down by 1 to force down the land by 1 block that the
            // Structure.field_236384_t_ field will place at bottom of the house. By lifting the house up by 1 and
            // lowering the bounding box, the land at bottom of house will now stay in place instead of also being
            // raise by 1 block because the land is based on the bounding box itself.
//            this.components.forEach(piece -> piece.offset(0, 1, 0));
//            this.components.forEach(piece -> piece.getBoundingBox().minY -= 1);

            VampireTowerPieces.start(templateManagerIn, blockpos, rotation, this.components, this.rand);

            // Sets the bounds of the structure once you are finished.
            this.recalculateStructureSize();

            // Where has it spawned?
            Monstervania.LOGGER.log(Level.DEBUG, "Vampire tower at: " + (blockpos.getX()) + " " + blockpos.getY() + " " + (blockpos.getZ()));
        }

    }

    //    @Override
//    public boolean func_225558_a_(BiomeManager arg0, ChunkGenerator arg1, Random arg2, int arg3, int arg4, Biome arg5){
//        return false;
//    }
//    @Override
//    public boolean canBeGenerated(BiomeManager manager, ChunkGenerator generator, Random rand, int chunkX, int chunkZ, Biome biome){
//        ChunkPos pos = this.getStartPositionForPosition(generator, rand, chunkX, chunkZ, 0, 0);
//        if(chunkX == pos.x && chunkZ == pos.z){
//            if(generator.hasStructure(biome, this)){
//                return true;
//            }
//        }
//        return false;
//    }


}
