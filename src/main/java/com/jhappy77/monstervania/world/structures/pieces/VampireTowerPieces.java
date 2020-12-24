package com.jhappy77.monstervania.world.structures.pieces;

import com.google.common.collect.ImmutableMap;
import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.init.UnconfiguredStructures;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class VampireTowerPieces {
    private static final ResourceLocation PART_1 = new ResourceLocation(Monstervania.MOD_ID, "vampire_tower");
    private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.of(PART_1, new BlockPos(0,1,0));
    public static void start(TemplateManager manager, BlockPos pos, Rotation rot, List<StructurePiece> pieces, Random rand){
        int x = pos.getX();
        int z = pos.getZ();
        BlockPos rotationOffset = new BlockPos(0,0,0).rotate(rot);
        BlockPos blockPos = rotationOffset.add(x, pos.getY(), z);
        pieces.add(new VampireTowerPieces.Piece(manager, PART_1, blockPos, rot));

        // Second piece
        /*BlockPos rotationOffset = new BlockPos(-10,0,0).rotate(rot);
        BlockPos blockPos = rotationOffset.add(x, pos.getY(), z);
        pieces.add(new VampireTowerPieces.Piece(manager, PART_2, blockPos, rot));*/

    }

    public static class Piece extends TemplateStructurePiece{
        private ResourceLocation resourceLocation;
        private Rotation rotation;

        public Piece(TemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos,
                     Rotation rotationIn) {
            super(UnconfiguredStructures.VAMPIRE_TOWER_PIECE, 0);
            this.resourceLocation = resourceLocationIn;
            BlockPos blockpos = VampireTowerPieces.OFFSET.get(resourceLocation);
            this.templatePosition = pos.add(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            this.rotation = rotationIn;
            this.setupPiece(templateManagerIn);
        }

        public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
            super(UnconfiguredStructures.VAMPIRE_TOWER_PIECE, tagCompound);
            this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
            this.setupPiece(templateManagerIn);
        }

        private void setupPiece(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation)
                    .setMirror(Mirror.NONE);
            this.setup(template, this.templatePosition, placementsettings);
        }

        @Override
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putString("Template", this.resourceLocation.toString());
            tagCompound.putString("Rot", this.rotation.name());
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
            if ("chest".equals(function)) {
                worldIn.setBlockState(pos, Blocks.CHEST.getDefaultState(), 2);
                TileEntity tileentity = worldIn.getTileEntity(pos);
                if (tileentity instanceof ChestTileEntity) {
                    // here you can set any loot tables for the chests
                    // ((ChestTileEntity) tileentity).setLootTable(<resource_location_to_loottable>, rand.nextLong());
                }
            }
        }

//        // create
//        @Override
//        public boolean create(IWorld worldIn, ChunkGenerator generator, Random randomIn,
//                                      MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPos) {
//            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation)
//                    .setMirror(Mirror.NONE);
//            BlockPos blockpos = VampireTowerPieces.OFFSET.get(this.resourceLocation);
//            this.templatePosition.add(Template.transformedBlockPos(placementsettings,
//                    new BlockPos(0 - blockpos.getX(), 0, 0 - blockpos.getZ())));
//
//            return super.func_225577_a_(worldIn, generator, randomIn, structureBoundingBoxIn, chunkPos);
//        }
    }
}
