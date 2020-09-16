package com.jhappy77.monstervania.blocks;

import com.jhappy77.monstervania.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class DarkStoneBrickStairs extends StairsBlock {
    public DarkStoneBrickStairs() {
        super(RegistryHandler.DARK_STONE_BRICKS_BLOCK.get().getDefaultState(),
                Block.Properties.create(Material.ROCK)
                        .hardnessAndResistance(1.5f, 6.0f)
                        .sound(SoundType.STONE)
                        .harvestLevel(0)
                        .harvestTool(ToolType.PICKAXE)
                        .setRequiresTool()
        );
    }
}
