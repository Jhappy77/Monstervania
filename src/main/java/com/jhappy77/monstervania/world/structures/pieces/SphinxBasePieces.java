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

//public class SphinxBasePieces {
//    //TODO: Make sure you change this every time you add a new structure!
//    private static final ResourceLocation PART_1 = new ResourceLocation(Monstervania.MOD_ID, "sphinx_base");
//    private static final ResourceLocation SPHINX_LAVA_VARIANT = new ResourceLocation(Monstervania.MOD_ID, "sphinx_lava_variant");
//    private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.of(PART_1, new BlockPos(0,1,0));
//    public static void start(TemplateManager manager, BlockPos pos, Rotation rot, List<StructurePiece> pieces, Random rand){
//        int x = pos.getX();
//        int z = pos.getZ();
//        BlockPos rotationOffset = new BlockPos(0,0,0).rotate(rot);
//        BlockPos blockPos = rotationOffset.add(x, pos.getY(), z);
//
//        //if(Math.random() > 0.5){
//            pieces.add(new com.jhappy77.monstervania.world.structures.pieces.SphinxBasePieces.Piece(manager, PART_1, blockPos, rot));
//          //  Monstervania.LOGGER.debug(blockPos.toString() + " has a regular sphinx!");
////        } else{
////            pieces.add(new com.jhappy77.monstervania.world.structures.pieces.SphinxBasePieces.Piece(manager, SPHINX_LAVA_VARIANT, blockPos, rot));
////            Monstervania.LOGGER.debug(blockPos.toString() + " has a lava sphinx!");
////        }
//        //TODO: Make sure you change this every time you add a new structure!
//
//
//    }
//
//    public static class Piece extends TemplateStructurePiece {
//        private ResourceLocation resourceLocation;
//        private Rotation rotation;
//
//        public Piece(TemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos,
//                     Rotation rotationIn) {
//            //TODO: Make sure you change this every time you add a new structure!
//            super(UnconfiguredStructures.SPHINX_BASE_PIECE, 0);
//            this.resourceLocation = resourceLocationIn;
//            //TODO: Make sure you change this every time you add a new structure!
//            BlockPos blockpos = com.jhappy77.monstervania.world.structures.pieces.SphinxBasePieces.OFFSET.get(resourceLocation);
//            this.templatePosition = pos.add(blockpos.getX(), blockpos.getY(), blockpos.getZ());
//            this.rotation = rotationIn;
//            this.setupPiece(templateManagerIn);
//        }
//
//        public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
//            //TODO: Make sure you change this every time you add a new structure!
//            super(UnconfiguredStructures.SPHINX_BASE_PIECE, tagCompound);
//
//            this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
//            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
//            this.setupPiece(templateManagerIn);
//        }
//
//        private void setupPiece(TemplateManager templateManager) {
//            Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
//            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation)
//                    .setMirror(Mirror.NONE);
//            this.setup(template, this.templatePosition, placementsettings);
//        }
//
//        @Override
//        protected void readAdditional(CompoundNBT tagCompound) {
//            super.readAdditional(tagCompound);
//            tagCompound.putString("Template", this.resourceLocation.toString());
//            tagCompound.putString("Rot", this.rotation.name());
//        }
//
//        @Override
//        protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
//            if ("chest".equals(function)) {
//                worldIn.setBlockState(pos, Blocks.CHEST.getDefaultState(), 2);
//                TileEntity tileentity = worldIn.getTileEntity(pos);
//                if (tileentity instanceof ChestTileEntity) {
//                    // here you can set any loot tables for the chests
//                    // ((ChestTileEntity) tileentity).setLootTable(<resource_location_to_loottable>, rand.nextLong());
//                }
//            }
//        }
//
//    }
//}
