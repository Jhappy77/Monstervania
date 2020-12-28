package com.jhappy77.monstervania.items;

import com.jhappy77.monstervania.Monstervania;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.fish.SalmonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class RatTailItem extends Item {
    public RatTailItem(){
        super(new Item.Properties()
                .group(Monstervania.TAB)
        );
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        //Monstervania.LOGGER.debug("onItemUse: " + itemstack.toString());
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
        if (ret != null) return ret;
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.resultPass(itemstack);
        } else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.resultPass(itemstack);
        } else {

            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
            BlockPos blockpos = blockraytraceresult.getPos();
            Direction direction = blockraytraceresult.getFace();
            //BlockPos blockpos1 = blockpos.offset(direction);
            //BlockState blockstate1 = worldIn.getBlockState(blockpos);

            // If you used the rat tail on water, randomly spawn appropriate fish in that water.
            if(worldIn.hasWater(blockpos)){
                randomlySpawnFish(worldIn, blockpos);
                if(!playerIn.isCreative())
                    itemstack.shrink(1);
                return ActionResult.resultConsume(itemstack);
            }
            return ActionResult.resultPass(itemstack);
        }
    }

    /**
     * Randomly spawns fish that were attracted by using the rat tail as bait
     * @param worldIn
     * @param pos
     */
    private void randomlySpawnFish(World worldIn, BlockPos pos) {

        double odds = Math.random();
        if(odds < 0.15){
            Biome biomeIn = worldIn.getBiome(pos);
            MobSpawnInfo biomeSpawnInfo = biomeIn.getMobSpawnInfo();

            List<MobSpawnInfo.Spawners> possibleSpawns = biomeSpawnInfo.getSpawners(EntityClassification.WATER_AMBIENT);
            if(possibleSpawns.isEmpty()){
                Monstervania.LOGGER.debug("biome was empty: " + biomeIn.toString());
                return;
            }

            // Selects a mob to spawn from weighted randoms
            MobSpawnInfo.Spawners toSpawn = WeightedRandom.getRandomItem(new Random(), possibleSpawns);
            Entity e = toSpawn.type.create(worldIn);
            e.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
            worldIn.addEntity(e);
        }

    }
}
