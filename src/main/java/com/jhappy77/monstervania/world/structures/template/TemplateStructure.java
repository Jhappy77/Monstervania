package com.jhappy77.monstervania.world.structures.template;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.util.MvSpawnCondition;
import com.jhappy77.monstervania.util.MvStructureSpawnInfo;
import com.jhappy77.monstervania.util.MvStructureSpawnable;
import com.jhappy77.monstervania.world.structures.FrostSpiderPitStructure;
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

import java.util.ArrayList;
import java.util.List;

/**
    public class TemplateStructure extends Structure<NoFeatureConfig> implements MvStructureSpawnable {

        public TemplateStructure(Codec<NoFeatureConfig> codec) {
            super(codec);
        }


        @Override
        public String getStructureName(){
            //TODO: Change path every time you add a new structure!
            return new ResourceLocation(Monstervania.MOD_ID, "template_structure").toString();
        }

        @Override
        public IStartFactory<NoFeatureConfig> getStartFactory()
        {
            //TODO: Change every time you add a new structure!
            return com.jhappy77.monstervania.world.structures.TemplateStructure.Start::new;
        }

        @Override
        public GenerationStage.Decoration getDecorationStage()
        {
            //TODO: Change every time you add a new structure!
            return GenerationStage.Decoration.SURFACE_STRUCTURES;
        }

        Handles calling up the structure's pieces class and height that structure will spawn at.
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
                TemplateStructurePieces.start(templateManagerIn, blockpos, rotation, this.components, this.rand);

                // Sets the bounds of the structure once you are finished.
                this.recalculateStructureSize();

                // Where has it spawned?
                //TODO: Change every time you add a new structure!
                Monstervania.LOGGER.log(Level.DEBUG, "Template structure at  " + (blockpos.getX()) + " " + blockpos.getY() + " " + (blockpos.getZ()));
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
            spawnConditions.add(new MvSpawnCondition<>(new MvStructureSpawnInfo()).restrictToLand()
            );
        }

    }
    **/

