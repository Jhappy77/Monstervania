package com.jhappy77.monstervania.world.structures;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.world.structures.pieces.FrostSpiderPitPieces;
import com.mojang.serialization.Codec;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.Level;

public class FrostSpiderPitStructure extends Structure<NoFeatureConfig> {

    public FrostSpiderPitStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }


    @Override
    public String getStructureName(){
        //TODO: Change path every time you add a new structure!
        return new ResourceLocation(Monstervania.MOD_ID, "frost_spider_pit").toString();
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory()
    {
        //TODO: Change every time you add a new structure!
        return FrostSpiderPitStructure.Start::new;
    }

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
            // OFFSET
            BlockPos blockpos = new BlockPos(x, y-9, z);

            //TODO: Change every time you add a new structure!
            FrostSpiderPitPieces.start(templateManagerIn, blockpos, rotation, this.components, this.rand);

            // Sets the bounds of the structure once you are finished.
            this.recalculateStructureSize();

            // Where has it spawned?
            //TODO: Change every time you add a new structure!
            Monstervania.LOGGER.log(Level.DEBUG, "Frost spider pit at: " + (blockpos.getX()) + " " + blockpos.getY() + " " + (blockpos.getZ()));
        }
    }

}