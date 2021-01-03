package com.jhappy77.monstervania.items;

import com.jhappy77.monstervania.Monstervania;
import com.jhappy77.monstervania.lists.ParticleList;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LightningWandItem extends Item {

    public LightningWandItem() {
        super(theProperties);
    }

    private static Item.Properties theProperties = new Item.Properties().setNoRepair().group(Monstervania.TAB).defaultMaxDamage(20);

    private boolean lightningPlaced = false;
    private BlockPos position;

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

            RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
            if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
                return ActionResult.resultPass(itemstack);
            } else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
                return ActionResult.resultPass(itemstack);
            } else {

                BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
                position = blockraytraceresult.getPos();
                Direction direction = blockraytraceresult.getFace();

                damageItem(itemstack, playerIn);

                spawnEnergyParticles(worldIn, position);

                lightningPlaced = true;
                return placeLightning(worldIn, playerIn, itemstack);
            }
    }

    private ActionResult<ItemStack> placeLightning(World worldIn, PlayerEntity playerIn, ItemStack itemStack){
        Monstervania.LOGGER.debug("Lightning placed!");
        if (position != null) {
            int y = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE, position.getX(), position.getZ());

            LightningBoltEntity theLightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);
            theLightning.setPosition(position.getX(), y, position.getZ());

            worldIn.addEntity(theLightning);
            lightningPlaced = false;
            damageItem(itemStack, playerIn);
            return ActionResult.resultSuccess(itemStack);
        } else {
            Monstervania.LOGGER.error("The position was null");
            return ActionResult.resultPass(itemStack);
        }
    }

    private void damageItem(ItemStack itemStack, PlayerEntity playerIn) {
        if (!playerIn.isCreative()) {
            itemStack.damageItem(1, playerIn, (entity) -> {
                entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }
    }


    @OnlyIn(Dist.CLIENT)
    private void spawnEnergyParticles(World worldIn, BlockPos pos) {
        int j = worldIn.rand.nextInt(5) + 5;
        for (int i = 0; i < j; i++) {
            float f1 = (float) ((worldIn.rand.nextInt(10)) / 4 * Math.pow(-1, worldIn.rand.nextInt(2)));
            float f2 = (float) ((worldIn.rand.nextInt(10)) / 4 * Math.pow(-1, worldIn.rand.nextInt(2)));
            float f3 = (float) ((worldIn.rand.nextInt(10)) / 4 * Math.pow(-1, worldIn.rand.nextInt(2)));
            worldIn.addParticle(ParticleList.ELECTRIC_PARTICLE.get(), pos.getX() + f1, pos.getY() + 1.0F + f2, pos.getZ() + f3, 0.0D, 0.0D, 0.0D);
        }
    }


}
