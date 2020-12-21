package com.jhappy77.monstervania.items;

import com.jhappy77.monstervania.Monstervania;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.fish.SalmonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.server.ServerWorld;

public class RatTailItem extends Item {
    public RatTailItem(){
        super(new Item.Properties()
                .group(Monstervania.TAB)
        );
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        Monstervania.LOGGER.debug("onItemUse: " + itemstack.toString());
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
        if (ret != null) return ret;
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            Monstervania.LOGGER.debug("Ray trace miss!");
            return ActionResult.resultPass(itemstack);
        } else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            Monstervania.LOGGER.debug("Ray trace not block!");
            return ActionResult.resultPass(itemstack);
        } else {

            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
            BlockPos blockpos = blockraytraceresult.getPos();
            Direction direction = blockraytraceresult.getFace();
            BlockPos blockpos1 = blockpos.offset(direction);
            BlockState blockstate1 = worldIn.getBlockState(blockpos);
            if(worldIn.hasWater(blockpos)){
                Monstervania.LOGGER.debug("Blockpos has water!");
                randomlySpawnFish(worldIn, blockpos);
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
        Biome biomeIn = worldIn.getBiome(pos);
        MobSpawnInfo biomeSpawnInfo = biomeIn.getMobSpawnInfo();
        SalmonEntity newSalmon = EntityType.SALMON.create(worldIn);
        newSalmon.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
        worldIn.addEntity(newSalmon);
        //newSalmon.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(pos), SpawnReason.BREEDING, (ILivingEntityData) null, (CompoundNBT) null);
        //EntityType.SALMON.spawn((ServerWorld) worldIn, context.getItem(), context.getPlayer(), pos, SpawnReason.BREEDING, true, false);
        Monstervania.LOGGER.debug("thought I spawned a salmon!");
    }
}
